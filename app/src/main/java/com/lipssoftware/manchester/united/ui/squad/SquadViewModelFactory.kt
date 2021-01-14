/*
 * Created by Dmitry Lipski on 14.01.21 15:00
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 14.01.21 15:00
 */

package com.lipssoftware.manchester.united.ui.squad

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class SquadViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SquadViewModel(context) as T
    }
}