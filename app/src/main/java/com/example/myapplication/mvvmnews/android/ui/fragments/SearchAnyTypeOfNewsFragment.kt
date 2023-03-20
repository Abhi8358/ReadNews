package com.example.myapplication.mvvmnews.android.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.SearchAnyTypeOfFragmentBinding
import com.example.myapplication.mvvmnews.android.adapters.NewsAdapter
import com.example.myapplication.mvvmnews.android.model.ArticleViewData
import com.example.myapplication.mvvmnews.android.ui.MainActivity
import com.example.myapplication.mvvmnews.android.ui.NewsViewModel
import com.example.myapplication.mvvmnews.android.utils.Constants
import com.example.myapplication.mvvmnews.android.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchAnyTypeOfNewsFragment : Fragment() {
    lateinit var binding: SearchAnyTypeOfFragmentBinding
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_any_type_of_fragment, container, false)

        setRecyclerView()
        setSearchBox()
        setSearchAdapter()
        setNavigationCLickListener()
        return binding.root
    }
    
    private fun setRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.searchNews.apply { 
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    
    private fun setSearchBox() {
        var job: Job? = null
        binding.newSearch.addTextChangedListener {editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                if (editable.toString().isNotEmpty())
                    viewModel.getSearchedNews(editable.toString())
            }
        }
    }

    private fun setSearchAdapter() {
        viewModel.searchedNews.observe(viewLifecycleOwner, Observer {resource ->
            when (resource) {
                is Resource.Success -> {
                    hideProgressBar()
                    resource.data?.let {
                        Log.d(TAG, "articles is " + resource.data.articles);
                        newsAdapter.differ.submitList(resource.data.articles)
                    }
                }
                is Resource.Error -> {
                    Log.d(TAG, "Error is ${resource.message}")
                }
                else -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    fun setNavigationCLickListener() {
        newsAdapter.setOnClickListener(object : NewsAdapter.OnClickListener {
            override fun onClick(articleUrl: String) {
                val bundle = Bundle()
                bundle.putString(Constants.ARTICLE_URL, articleUrl)
                findNavController().navigate(
                    R.id.action_searchAnyTypeOfNews_to_articlesFragment,
                    bundle
                )
            }

            override fun onLongClick(article: ArticleViewData): Boolean {
                viewModel.saveArticle(article)
                Toast.makeText(requireContext(), "Article Saved Successfully", Toast.LENGTH_SHORT).show()
                return true
            }
        })
    }
}