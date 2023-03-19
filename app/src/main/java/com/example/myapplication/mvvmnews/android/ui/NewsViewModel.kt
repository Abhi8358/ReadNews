package com.example.myapplication.mvvmnews.android.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.mvvmnews.android.model.NewsResponseViewData
import com.example.myapplication.mvvmnews.android.repositories.NewsRepository
import com.example.myapplication.mvvmnews.android.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val repository: NewsRepository) : ViewModel() {

    val latestNews: MutableLiveData<Resource<NewsResponseViewData>> = MutableLiveData()
    val searchedNews: MutableLiveData<Resource<NewsResponseViewData>> = MutableLiveData()
    private val latestPage: Int = 1
    private val searchedPage: Int = 1

    init {
        getLatestNews("us")
    }
    private fun getLatestNews(countryCode: String) =
        viewModelScope.launch {
            latestNews.postValue(Resource.Loading())
            val response = repository.getLatestNews(countryCode,latestPage)
            latestNews.postValue(handleNewsResponse(response))
            Log.d("AAAAAAAA", "response data = "+response.body());
    }

    fun getSearchedNews(countryCode: String) =
        viewModelScope.launch {
            searchedNews.postValue(Resource.Loading())
            val response = repository.getSearchNews(countryCode,searchedPage)
            searchedNews.postValue(handleSearchedNewsResponse(response))
            Log.d("AAAAAAAA", "response data = "+response.body());
        }

    private fun handleNewsResponse(response: Response<NewsResponseViewData>) : Resource<NewsResponseViewData> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Log.d("AAAAAAAA", "response data body = "+response.body())
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchedNewsResponse(response: Response<NewsResponseViewData>) : Resource<NewsResponseViewData> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Log.d("AAAAAAAA", "response data body = "+response.body())
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}