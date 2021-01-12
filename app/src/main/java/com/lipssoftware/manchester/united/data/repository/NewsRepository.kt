/*
 * Created by Dmitry Lipski on 12.01.21 16:56
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12.01.21 10:08
 */

package com.lipssoftware.manchester.united.data.repository

import com.lipssoftware.manchester.united.data.database.NewsDao
import com.lipssoftware.manchester.united.data.model.news.NewsDomain
import com.lipssoftware.manchester.united.data.network.NewsService
import com.lipssoftware.manchester.united.utils.convertStringToDate

class NewsRepository(
        private val newsService: NewsService,
        private val newsDao: NewsDao
) : Repository {

    suspend fun getNews(): List<NewsDomain> {
        refreshNews()
        return newsDao.getNews()
    }

    private suspend fun refreshNews() {
        try {
            val result = newsService.getNews().channel
            result.items.let { list ->
                val listDomain = list.map {
                    NewsDomain(
                            it.id,
                            it.title,
                            it.link,
                            convertStringToDate(it.pubDate),
                            it.newsText,
                            it.thumbnail.url
                    )
                }
                newsDao.insertNews(listDomain)
            }
        } catch (exception: Exception) {
            println(exception.message)
        }
    }
}