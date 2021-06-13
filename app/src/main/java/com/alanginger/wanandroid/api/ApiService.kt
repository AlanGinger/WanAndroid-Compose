package com.alanginger.wanandroid.api

import com.alanginger.wanandroid.model.ArticleList
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("article/list/{page}/json")
    suspend fun fetchArticleList(@Path("page") page: Int = 0): ResponseResult<ArticleList>
}