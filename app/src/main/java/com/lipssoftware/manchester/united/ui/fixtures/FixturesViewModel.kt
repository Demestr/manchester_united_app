/*
 * Created by Dmitry Lipski on 25.01.21 13:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 25.01.21 10:53
 */

package com.lipssoftware.manchester.united.ui.fixtures

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lipssoftware.manchester.united.data.model.domain.MatchDomain
import com.lipssoftware.manchester.united.data.repository.FixturesRepository
import com.lipssoftware.manchester.united.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FixturesViewModel @ViewModelInject constructor(private val repository: FixturesRepository) :
    ViewModel() {

    private val _standings = MutableLiveData<Resource<List<MatchDomain>>>()
    val standings: LiveData<Resource<List<MatchDomain>>> = _standings
    var indexOfLastPlayedMatch: Int = -1

    init {
        getStandings()
    }

    private fun getStandings() {
        viewModelScope.launch(Dispatchers.IO) {
            _standings.postValue(Resource.loading(data = null))
            try {
                val list = repository.getFixtures().sortedBy { it.timestamp }
                val lastPlayedMatch = list.filter {
                    it.statusShort == "FT" ||
                            it.statusShort == "1H" ||
                            it.statusShort == "HT" ||
                            it.statusShort == "2H" ||
                            it.statusShort == "ET" ||
                            it.statusShort == "P" ||
                            it.statusShort == "FT"
                }.maxByOrNull { it.timestamp }
                indexOfLastPlayedMatch = list.indexOf(lastPlayedMatch)
                _standings.postValue(Resource.success(data = list))
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