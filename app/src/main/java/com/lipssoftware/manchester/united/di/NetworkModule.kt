/*
 * Created by Dmitry Lipski on 25.01.21 13:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 25.01.21 11:11
 */

package com.lipssoftware.manchester.united.di

import com.lipssoftware.manchester.united.BuildConfig
import com.lipssoftware.manchester.united.data.network.NewsService
import com.lipssoftware.manchester.united.data.network.StatsService
import com.lipssoftware.manchester.united.utils.BASE_URL_NEWS
import com.lipssoftware.manchester.united.utils.BASE_URL_STATS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StatsRetrofit

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @NewsRetrofit
    @Provides
    @Singleton
    fun provideNewsRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_NEWS)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideNewsApiService(@NewsRetrofit retrofit: Retrofit): NewsService =
        retrofit.create(NewsService::class.java)

    @StatsRetrofit
    @Provides
    @Singleton
    fun provideStatsRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_STATS)
        .client(OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    val apiKey = BuildConfig.API_KEY
                    builder.header("X-RapidAPI-Key", apiKey)
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideStatsApiService(@StatsRetrofit retrofit: Retrofit): StatsService =
        retrofit.create(StatsService::class.java)

}