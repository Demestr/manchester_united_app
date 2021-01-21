/*
 * Created by Dmitry Lipski on 20.01.21 16:30
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 20.01.21 15:50
 */

package com.lipssoftware.manchester.united.data.repository

import com.lipssoftware.manchester.united.data.database.NewsDao
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.data.network.NewsService

class NewsRepository(
        private val newsService: NewsService,
        private val newsDao: NewsDao
) : Repository {

    suspend fun getNews(): List<NewsDomain> {
        return newsDao.getNews()
    }

    suspend fun refreshNews(notify: (news: NewsDomain?) -> Unit) {
        try {
            val result = newsService.getNews().channel
            result.items.let { list ->
                val listDomain = list.map {
                    it.toNewsDomain()
                }
                //if(!newsDao.getNews().isNullOrEmpty() && !newsDao.isExist(listDomain.last().id)){
                if(!newsDao.isExist(listDomain.last().id)){
                    notify(listDomain.first())
                }
                newsDao.insertNews(listDomain)
            }
        } catch (exception: Exception) {
            println(exception.message)
        }
    }
}