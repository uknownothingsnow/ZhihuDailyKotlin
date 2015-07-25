package com.github.lzyzsd.zhihudailykotlin

import retrofit.http.GET
import retrofit.http.Path

/**
 * Created by bruce on 7/17/15.
 */
interface RestApi {
    @GET("/news/latest")
    fun getPostList(): StoryList

    @GET("/story/{id}")
    fun getStoryDetail(@Path("id") id: Long): StoryDetail
}