/*
 * Created by Dmitry Lipski on 19.01.21 16:24
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 19.01.21 16:23
 */

package com.lipssoftware.manchester.united.ui.fixtures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lipssoftware.manchester.united.data.model.fixtures.Match
import com.lipssoftware.manchester.united.data.repository.FixturesRepository
import com.lipssoftware.manchester.united.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FixturesViewModel(private val repository: FixturesRepository) : ViewModel() {

    private val _standings = MutableLiveData<Resource<List<Match>>>()
    val standings: LiveData<Resource<List<Match>>> = _standings

    init {
        getStandings()
    }

    private fun getStandings(){
        viewModelScope.launch(Dispatchers.IO) {
            _standings.postValue(Resource.loading(data = null))
            try {
                _standings.postValue(Resource.success(data = repository.getFixtures()))
            }
            catch (exception: Exception){
                _standings.postValue(Resource.error(data = null, message = exception.message ?: "Error occurred!"))
            }
        }
    }
}