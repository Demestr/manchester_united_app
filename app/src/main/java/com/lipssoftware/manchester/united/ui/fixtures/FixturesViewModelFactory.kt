/*
 * Created by Dmitry Lipski on 19.01.21 16:24
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 19.01.21 16:24
 */

package com.lipssoftware.manchester.united.ui.fixtures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lipssoftware.manchester.united.data.repository.FixturesRepository

@Suppress("UNCHECKED_CAST")
class FixturesViewModelFactory(private val fixturesRepository: FixturesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FixturesViewModel(fixturesRepository) as T
    }
}