/*
 * Created by Dmitry Lipski on 11.01.21 17:05
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 13:45
 */

package com.lipssoftware.manchester.united.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lipssoftware.manchester.united.data.repository.NewsRepository

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(private val newsRepository: NewsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}