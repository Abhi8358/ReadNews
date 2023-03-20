package com.example.myapplication.mvvmnews.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.SavedNewsFragmentBinding
import com.example.myapplication.mvvmnews.android.adapters.NewsAdapter
import com.example.myapplication.mvvmnews.android.model.ArticleViewData
import com.example.myapplication.mvvmnews.android.ui.MainActivity
import com.example.myapplication.mvvmnews.android.ui.NewsViewModel
import com.example.myapplication.mvvmnews.android.utils.Constants
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment : Fragment(R.layout.saved_news_fragment) {
    lateinit var binding: SavedNewsFragmentBinding
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.saved_news_fragment, container, false)
        viewModel = (activity as MainActivity).viewModel

        Toast.makeText(requireContext(), "Swipe Left to delete article", Toast.LENGTH_LONG).show()
        setUpRecyclerView();
        setNewsAdapter();
        deleteBySwipeArticle()
        setNavigationCLickListener()
        return binding.root
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.savedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun setNewsAdapter() {
        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer {
            newsAdapter.differ.submitList(it)
        })
    }

    private fun deleteBySwipeArticle() {
        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val article = newsAdapter.differ.currentList[viewHolder.adapterPosition]
                viewModel.deleteArticle(article)
                view?.let {
                    Snackbar.make(it, "Deleted Successfully", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo") {
                            viewModel.saveArticle(article)
                        }
                        show()
                    }
                }
            }

        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.savedNews)
        }
    }

    fun setNavigationCLickListener() {
        newsAdapter.setOnClickListener(object : NewsAdapter.OnClickListener {
            override fun onClick(articleUrl: String) {
                val bundle = Bundle()
                bundle.putString(Constants.ARTICLE_URL, articleUrl)
                findNavController().navigate(
                    R.id.action_savedNewsFragment_to_articlesFragment,
                    bundle
                )
            }

            override fun onLongClick(article: ArticleViewData): Boolean {
                return true
            }
        })
    }
}