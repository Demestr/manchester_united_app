/*
 * Created by Dmitry Lipski on 21.01.21 14:58
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 21.01.21 13:01
 */

package com.lipssoftware.manchester.united.data.repository

import com.lipssoftware.manchester.united.data.database.NewsDao
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.data.network.NewsService
import kotlinx.coroutines.flow.Flow

class NewsRepository(
        private val newsService: NewsService,
        private val newsDao: NewsDao
) : Repository {

    val newsFlow: Flow<List<NewsDomain>>
        get() = newsDao.getNews()

    suspend fun refreshNews(notify: (news: NewsDomain?) -> Unit) {
        try {
            val result = newsService.getNews().channel
            result.items.let { list ->
                val listDomain = list.map {
                    it.toNewsDomain()
                }
                if(newsDao.newsCount() != 0 && !newsDao.isExist(listDomain.first().id)){
                    notify(listDomain.first())
                }
                newsDao.insertNews(listDomain)
            }
        } catch (exception: Exception) {
            println(exception.message)
        }
    }

    suspend fun deleteNews(id: String){
        newsDao.deleteNews(id)
    }
}