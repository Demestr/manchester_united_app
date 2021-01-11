/*
 * Created by Dmitry Lipski on 11.01.21 17:05
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 15:40
 */

package com.lipssoftware.manchester.united.data.network

import com.lipssoftware.manchester.united.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object NewsBuilder{

    private const val BASE_URL = "https://www.manutd.com/Feeds/"
    val newsService: NewsService = getRetrofit().create(NewsService::class.java)

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

}

object StatsBuilder {

    private const val BASE_URL = "https://v3.football.api-sports.io/"

    private val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor { chain ->
                val builder = chain.request().newBuilder()
                val apiKey = BuildConfig.API_KEY
                builder.header("X-RapidAPI-Key", apiKey)
                return@Interceptor chain.proceed(builder.build())
            }
        )
    }.build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val statsService: StatsService = getRetrofit().create(StatsService::class.java)
}