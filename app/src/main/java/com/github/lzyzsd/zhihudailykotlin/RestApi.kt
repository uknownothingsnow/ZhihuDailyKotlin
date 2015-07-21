package com.github.lzyzsd.zhihudailykotlin

import retrofit.http.GET

/**
 * Created by bruce on 7/17/15.
 */
interface RestApi {
    @GET("/news/latest")
    fun getPostList(): StoryList
}