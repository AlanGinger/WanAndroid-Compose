package com.alanginger.wanandroid.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanginger.wanandroid.api.Api
import com.alanginger.wanandroid.model.ArticleList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    val articleListLiveData: MutableLiveData<ArticleList> = MutableLiveData()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = Api.service.fetchArticleList()
                withContext(Dispatchers.Main) {
                    articleListLiveData.value = result.data
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", e.localizedMessage, e)
            }
        }
    }
}