/*
 * Created by Dmitry Lipski on 09.02.21 17:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09.02.21 17:05
 */

package com.lipssoftware.manchester.united.data.repository

import android.annotation.SuppressLint
import com.lipssoftware.manchester.united.data.database.NewsDao
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.data.network.NewsService
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsService: NewsService,
    private val newsDao: NewsDao
) : Repository {

    fun getNews() = newsDao.getNews()

    @SuppressLint("CheckResult")
    fun refreshNews(notify: (news: NewsDomain?) -> Unit) {
        try {
            newsService.getNews().subscribeOn(Schedulers.io()).subscribe { rssFeed ->
                rssFeed.channel.items.let { list ->
                    val listDomain = list.map {
                        it.toNewsDomain()
                    }
                    if (newsDao.newsCount() != 0 && !newsDao.isExist(listDomain.first().id)) {
                        notify(listDomain.first())
                    }
                    newsDao.insertNews(listDomain)
                }
            }
        } catch (exception: Exception) {
            println(exception.message)
        }
    }
}