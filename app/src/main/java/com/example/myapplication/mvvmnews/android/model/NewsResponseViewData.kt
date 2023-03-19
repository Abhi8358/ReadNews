package com.example.myapplication.mvvmnews.android.model

import com.example.myapplication.mvvmnews.android.model.ArticleViewData

data class NewsResponseViewData(
    val articles: List<ArticleViewData>,
    val status: String,
    val totalResults: Int
)