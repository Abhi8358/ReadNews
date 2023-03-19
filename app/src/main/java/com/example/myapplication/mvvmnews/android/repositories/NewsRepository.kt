package com.example.myapplication.mvvmnews.android.repositories

import com.example.myapplication.mvvmnews.android.api.RetrofitInstance
import com.example.myapplication.mvvmnews.android.dao.ArticleDatabase
import com.example.myapplication.mvvmnews.android.model.NewsResponseViewData
import kotlinx.coroutines.internal.artificialFrame
import retrofit2.Response
import retrofit2.Retrofit

class NewsRepository(
    articleDatabase: ArticleDatabase
) {
    suspend fun getLatestNews(countryCode: String, pageNumber: Int): Response<NewsResponseViewData> {
        return RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)
    }
}