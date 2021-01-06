/*
 * Created by Dmitry Lipski on 06.01.21 12:54
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 06.01.21 12:54
 */

package com.lipssoftware.manchester.united.data.network

import com.lipssoftware.manchester.united.utils.API_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StatsBuilder {

    private const val BASE_URL = "https://v3.football.api-sports.io/"

    private val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("X-RapidAPI-Key", API_KEY)
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