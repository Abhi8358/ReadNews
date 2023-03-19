package com.example.myapplication.mvvmnews.android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "articles"
)
data class ArticleViewData(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceViewData,
    val title: String,
    val url: String,
    val urlToImage: String
)