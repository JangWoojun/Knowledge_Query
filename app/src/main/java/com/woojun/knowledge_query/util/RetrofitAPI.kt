package com.woojun.knowledge_query.util

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitAPI {
    @POST("MRCServlet")
    fun MRPost(
        @Header("Authorization") accessToken: String,
        @Body requestBody: RequestBody
    ): Call<MRResult>

    @POST("WikiQA")
    fun WKPost(
        @Header("Authorization") accessToken: String,
        @Body requestBody: RequestBody2
    ): Call<WKResult>
}