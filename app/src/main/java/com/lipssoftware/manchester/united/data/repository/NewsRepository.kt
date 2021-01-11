/*
 * Created by Dmitry Lipski on 11.01.21 17:05
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 16:14
 */

package com.lipssoftware.manchester.united.data.repository

import com.lipssoftware.manchester.united.data.model.news.RssItem
import com.lipssoftware.manchester.united.data.network.NewsService

class NewsRepository(
    private val newsService: NewsService
    //private val standingsDao: StandingsDao
    ): Repository {

    suspend fun getNews(): List<RssItem>{
        val news = newsService.getNews()
        return news.channel.items
    }
}