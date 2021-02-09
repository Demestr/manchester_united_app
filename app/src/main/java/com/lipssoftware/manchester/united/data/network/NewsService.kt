/*
 * Created by Dmitry Lipski on 09.02.21 17:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09.02.21 17:05
 */

package com.lipssoftware.manchester.united.data.network

import com.lipssoftware.manchester.united.data.model.news.RssFeed
import io.reactivex.Observable
import retrofit2.http.GET

interface NewsService {

    @GET("NewsSecondRSSFeed")
    fun getNews(): Observable<RssFeed>
}