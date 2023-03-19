package com.example.myapplication.mvvmnews.android.api

import com.example.myapplication.mvvmnews.android.model.NewsResponseViewData
import com.example.myapplication.mvvmnews.android.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.Query
import retrofit2.http.GET

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        country: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponseViewData>

    @GET("v2/everything")
    suspend fun getSearchNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("api_key")
        api_key: String = API_KEY
    ): Response<NewsResponseViewData>

}