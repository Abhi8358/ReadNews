package com.example.myapplication.mvvmnews.android.repositories

import androidx.lifecycle.LiveData
import com.example.myapplication.mvvmnews.android.api.RetrofitInstance
import com.example.myapplication.mvvmnews.android.dao.ArticleDatabase
import com.example.myapplication.mvvmnews.android.model.ArticleViewData
import com.example.myapplication.mvvmnews.android.model.NewsResponseViewData
import retrofit2.Response

class NewsRepository(
    private val articleDatabase: ArticleDatabase
) {
    suspend fun getLatestNews(countryCode: String, pageNumber: Int): Response<NewsResponseViewData> {
        return RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)
    }

    suspend fun getSearchNews(typedWord: String, pageNumber: Int): Response<NewsResponseViewData> {
        return RetrofitInstance.api.getSearchNews(typedWord, pageNumber)
    }

    suspend fun upsetArticle(article: ArticleViewData): Long {
        return articleDatabase.getArticleDao().upsert(article)
    }

    suspend fun deleteArticle(article: ArticleViewData) {
        return articleDatabase.getArticleDao().deleteArticle(article)
    }

    fun getSavedArticles(): LiveData<List<ArticleViewData>> {
        return articleDatabase.getArticleDao().getAllArticles()
    }
}