/*
 * Created by Dmitry Lipski on 11.01.21 12:53
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 9:43
 */

package com.lipssoftware.manchester.united.ui.standings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lipssoftware.manchester.united.data.repository.StandingsRepository

@Suppress("UNCHECKED_CAST")
class StandingsViewModelFactory(private val standingsRepository: StandingsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StandingsViewModel(standingsRepository) as T
    }
}