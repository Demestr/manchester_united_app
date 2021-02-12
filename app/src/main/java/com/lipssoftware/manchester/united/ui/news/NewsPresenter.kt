/*
 * Created by Dmitry Lipski on 12.02.21 9:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12.02.21 8:52
 */

package com.lipssoftware.manchester.united.ui.news

import android.view.View
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.data.repository.NewsRepository
import com.lipssoftware.manchester.united.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsPresenter @Inject constructor(
    private val newsRepository: NewsRepository): BasePresenter<NewsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        fetchNewsFromRemote()
        loadNews()
    }

    fun onRefresh() {
        fetchNewsFromRemote()
        viewState.showLoading(false)
    }

    fun onNewsClick(newsDomain: NewsDomain, view: View){
        viewState.openNewsDetail(newsDomain, view)
    }

    private fun loadNews() {
        viewState.showLoading(true)
        compositeDisposable.add(newsRepository
            .getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { matchesList ->
                    viewState.updateNews(matchesList)
                    viewState.showLoading(false)
                },
                { viewState.showError(it.message ?: "Error") }))
    }

    private fun fetchNewsFromRemote() {
        newsRepository.refreshNews {
            viewState.showIsUpdatedMessage("News has been updated")
        }
    }
}