/*
 * Created by Dmitry Lipski on 11.01.21 17:05
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 16:14
 */

package com.lipssoftware.manchester.united.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lipssoftware.manchester.united.data.model.news.NewsItem
import com.lipssoftware.manchester.united.data.repository.NewsRepository
import com.lipssoftware.manchester.united.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _news = MutableLiveData<Resource<List<NewsItem>>>()
    val news: LiveData<Resource<List<NewsItem>>>
        get() = _news

    fun getNews(){
        viewModelScope.launch(Dispatchers.IO) {
            _news.postValue(Resource.loading(data = null))
            try {
                _news.postValue(newsRepository.getNews().let { list -> Resource.success(data = list.map { NewsItem(it.title, it.link, it.pubDate, it.newsText, it.thumbnail.url) }) })
            }
            catch (exception: Exception){
                _news.postValue(Resource.error(data = null, message = exception.message ?: "Error occurred"))
            }
        }
    }
}