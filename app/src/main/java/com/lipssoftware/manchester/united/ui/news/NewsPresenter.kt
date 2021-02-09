/*
 * Created by Dmitry Lipski on 09.02.21 17:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09.02.21 17:05
 */

package com.lipssoftware.manchester.united.ui.news

import com.lipssoftware.manchester.united.data.repository.NewsRepository
import com.lipssoftware.manchester.united.ui.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsPresenter @Inject constructor(
    private val newsRepository: NewsRepository): BasePresenter<NewsView> {

    var view: NewsView? = null
    var news: Disposable? = null

    init {
        fetchNewsFromRemote()
    }


    private fun loadNews() {
        //viewState.showProgress(false)
        news = newsRepository
            .getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { matchesList ->
                    view?.loadNews(matchesList)
                    view?.showProgress(false)
                },
                { view?.showError(it.message ?: "Error") })
    }

    fun onRefresh() {
        fetchNewsFromRemote()
        view?.showProgress(false)
    }

    private fun fetchNewsFromRemote() {
        newsRepository.refreshNews { }
    }

    override fun attachView(view: NewsView) {
        this.view = view
        loadNews()
    }

    override fun detachView() {
        this.view = null
    }

    override fun destroy() {
        news?.dispose()
    }
}