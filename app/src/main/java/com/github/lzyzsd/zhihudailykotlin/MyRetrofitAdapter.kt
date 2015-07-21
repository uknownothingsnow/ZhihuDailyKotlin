package com.github.lzyzsd.zhihudailykotlin

import retrofit.RestAdapter

/**
 * Created by bruce on 7/17/15.
 */
object MyRetrofitAdapter {
    val adapter : RestAdapter = RestAdapter.Builder()
            .setEndpoint("http://news-at.zhihu.com/api/3")
            .build();

    val restApi : RestApi = adapter.create(javaClass<RestApi>())
}