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

class ArticlesFragment : Fragment(R.layout.articles_fragment) {

    lateinit var binding: ArticlesFragmentBinding
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.latest_news_fragment, container, false)
        return binding.root
    }
}