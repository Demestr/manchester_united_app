/*
 * Created by Dmitry Lipski on 12.01.21 16:56
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12.01.21 15:02
 */

package com.lipssoftware.manchester.united.ui.fullnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lipssoftware.manchester.united.data.model.news.NewsDomain

class FullNewsViewModel : ViewModel() {

    private val _news = MutableLiveData<NewsDomain>()
    val news: LiveData<NewsDomain>
    get() = _news

    fun setNews(news: NewsDomain){
        _news.value = news
    }
}