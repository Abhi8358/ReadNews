package com.example.myapplication.mvvmnews.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.ArticlesFragmentBinding
import com.example.myapplication.mvvmnews.android.ui.MainActivity
import com.example.myapplication.mvvmnews.android.ui.NewsViewModel
import com.example.myapplication.mvvmnews.android.utils.Constants.Companion.ARTICLE_URL

class ArticlesFragment : Fragment() {

    lateinit var binding: ArticlesFragmentBinding
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.articles_fragment, container, false)
        viewModel = (activity as MainActivity).viewModel
        setArticleInWebView()
        return binding.root
    }

    private fun setArticleInWebView() {
        val articleUrl: String = arguments?.getString(ARTICLE_URL).toString()
        binding.viewArticleInWebView.loadUrl(articleUrl)
    }
}