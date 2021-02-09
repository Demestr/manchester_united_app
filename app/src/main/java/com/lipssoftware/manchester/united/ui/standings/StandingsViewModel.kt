/*
 * Created by Dmitry Lipski on 09.02.21 17:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09.02.21 9:36
 */

package com.lipssoftware.manchester.united.ui.standings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain
import com.lipssoftware.manchester.united.data.repository.StandingsRepository
import com.lipssoftware.manchester.united.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StandingsViewModel @ViewModelInject constructor(private val repository: StandingsRepository) :
    ViewModel() {

    private val _standings = MutableLiveData<Resource<List<StandingDomain>>>()
    val standings: LiveData<Resource<List<StandingDomain>>> = _standings

    init {
        getStandings()
    }

    private fun getStandings() {
        viewModelScope.launch(Dispatchers.IO) {
            _standings.postValue(Resource.loading(data = null))
            try {
                repository.getStandings().subscribe { _standings.postValue(Resource.success(data = it)) }
            } catch (exception: Exception) {
                _standings.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error occurred!"
                    )
                )
            }
        }
    }
}