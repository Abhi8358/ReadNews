package com.example.myapplication.mvvmnews.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.mvvmnews.android.dao.ArticleDatabase
import com.example.myapplication.mvvmnews.android.repositories.NewsRepository

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = NewsRepository(ArticleDatabase.invoke(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(repository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomNavigationView.setupWithNavController(binding.flFragment[0].findNavController())
    }
}