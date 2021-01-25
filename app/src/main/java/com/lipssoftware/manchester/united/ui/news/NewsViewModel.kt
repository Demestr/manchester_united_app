/*
 * Created by Dmitry Lipski on 25.01.21 13:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 25.01.21 10:53
 */

package com.lipssoftware.manchester.united.ui.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    val news: LiveData<List<NewsDomain>>
        get() = newsRepository.newsFlow.asLiveData()


    fun fetchNewsFromRemote() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.refreshNews { }
        }
    }
}