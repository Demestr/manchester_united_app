/*
 * Created by Dmitry Lipski on 21.01.21 14:58
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 21.01.21 14:58
 */

package com.lipssoftware.manchester.united.ui.news

import androidx.lifecycle.*
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.data.repository.NewsRepository
import com.lipssoftware.manchester.united.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _news = MutableLiveData<Resource<List<NewsDomain>>>()
    val news: LiveData<List<NewsDomain>>
        get() = newsRepository.newsFlow.asLiveData()

//    init {
//        getNews()
//    }
//
//    private fun getNews() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _news.postValue(Resource.loading(data = null))
//            newsRepository.getNews().collect { value -> value }
//            try {
//                _news.postValue(Resource.success(data = newsRepository.getNews()))
//            } catch (exception: Exception) {
//                _news.postValue(
//                    Resource.error(
//                        data = null,
//                        message = exception.message ?: "Error occurred"
//                    )
//                )
//            }
//        }
//    }

    fun deleteNews(){
        viewModelScope.launch(Dispatchers.IO) {
           newsRepository.deleteNews("{74329359-B82E-4B5B-8BFC-C774265E0926}")
        }
    }

    fun fetchNewsFromRemote(){
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.refreshNews {  }
        }
    }
}