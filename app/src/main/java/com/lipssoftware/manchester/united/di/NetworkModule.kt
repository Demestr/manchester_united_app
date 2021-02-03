/*
 * Created by Dmitry Lipski on 03.02.21 11:43
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 28.01.21 9:22
 */

package com.lipssoftware.manchester.united.di

import android.content.Context
import androidx.work.WorkManager
import com.lipssoftware.manchester.united.BuildConfig
import com.lipssoftware.manchester.united.data.network.NewsService
import com.lipssoftware.manchester.united.data.network.StatsService
import com.lipssoftware.manchester.united.utils.BASE_URL_NEWS
import com.lipssoftware.manchester.united.utils.BASE_URL_STATS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context) = WorkManager.getInstance(context)
}