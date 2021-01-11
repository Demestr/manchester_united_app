/*
 * Created by Dmitry Lipski on 11.01.21 17:05
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 13:50
 */

package com.lipssoftware.manchester.united.data.network

import com.lipssoftware.manchester.united.data.model.news.RssFeed
import retrofit2.http.GET

interface NewsService {

    @GET("NewsSecondRSSFeed")
    suspend fun getNews(): RssFeed
}