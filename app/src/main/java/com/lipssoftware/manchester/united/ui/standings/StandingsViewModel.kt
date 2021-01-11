/*
 * Created by Dmitry Lipski on 11.01.21 17:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 15:16
 */

package com.lipssoftware.manchester.united.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain
import com.lipssoftware.manchester.united.data.repository.StandingsRepository
import com.lipssoftware.manchester.united.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StandingsViewModel(private val repository: StandingsRepository) : ViewModel() {

    private val _standings = MutableLiveData<Resource<List<StandingDomain>>>()
    val standings: LiveData<Resource<List<StandingDomain>>> = _standings

    init {
        getStandings()
    }

    private fun getStandings(){
        viewModelScope.launch(Dispatchers.IO) {
            _standings.postValue(Resource.loading(data = null))
            try {
                _standings.postValue(Resource.success(data = repository.getStandings()))
            }
            catch (exception: Exception){
                _standings.postValue(Resource.error(data = null, message = exception.message ?: "Error occurred!"))
            }
        }
    }
}