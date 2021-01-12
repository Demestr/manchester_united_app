/*
 * Created by Dmitry Lipski on 12.01.21 16:56
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12.01.21 16:49
 */

package com.lipssoftware.manchester.united.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lipssoftware.manchester.united.data.model.news.NewsDomain
import com.lipssoftware.manchester.united.data.repository.NewsRepository
import com.lipssoftware.manchester.united.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _news = MutableLiveData<Resource<List<NewsDomain>>>()
    val news: LiveData<Resource<List<NewsDomain>>>
        get() = _news

    init {
        getNews()
    }

    fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            _news.postValue(Resource.loading(data = null))
            try {
                _news.postValue(Resource.success(data = newsRepository.getNews()))
            } catch (exception: Exception) {
                _news.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error occurred"
                    )
                )
            }
        }
    }
}