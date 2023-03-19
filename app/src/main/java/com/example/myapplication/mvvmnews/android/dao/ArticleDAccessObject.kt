package com.example.myapplication.mvvmnews.android.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.mvvmnews.android.model.ArticleViewData

@Dao
interface ArticleDAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: ArticleViewData) : Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<ArticleViewData>>

    @Delete
    suspend fun deleteArticle(article: ArticleViewData)
}