package com.example.myapplication.mvvmnews.android.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.LatestNewsFragmentBinding
import com.example.myapplication.mvvmnews.android.adapters.NewsAdapter
import com.example.myapplication.mvvmnews.android.model.ArticleViewData
import com.example.myapplication.mvvmnews.android.ui.MainActivity
import com.example.myapplication.mvvmnews.android.ui.NewsViewModel
import com.example.myapplication.mvvmnews.android.utils.Constants.Companion.ARTICLE_URL
import com.example.myapplication.mvvmnews.android.utils.Resource
import com.google.android.material.snackbar.Snackbar

const val TAG = "LatestNewsFragment"

class LatestNewsFragment : Fragment() {
    lateinit var binding: LatestNewsFragmentBinding
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.latest_news_fragment, container, false)
        viewModel = (activity as MainActivity).viewModel
        setRecyclerView()
        viewModel.latestNews.observe(viewLifecycleOwner, Observer { resource ->

            when (resource) {
                is Resource.Success -> {
                    hideProgressBar()
                    resource.data?.let {
                        Log.d(TAG, "articles is " + resource.data.articles)
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
        setNavigationCLickListener()
        return binding.root
    }

    private fun setRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.breakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
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
                bundle.putString(ARTICLE_URL, articleUrl)
                findNavController().navigate(
                    R.id.action_latestNewsFragment_to_articlesFragment,
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