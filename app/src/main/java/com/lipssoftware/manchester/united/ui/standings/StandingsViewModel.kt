/*
 * Created by Dmitry Lipski on 05.01.21 12:38
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 05.01.21 12:32
 */

package com.lipssoftware.manchester.united.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lipssoftware.manchester.united.data.model.standings.Standing
import com.lipssoftware.manchester.united.data.network.StatsBuilder
import com.lipssoftware.manchester.united.utils.PREMIER_LEAGUE_ID
import com.lipssoftware.manchester.united.utils.SEASON
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StandingsViewModel : ViewModel() {

    private val _standings = MutableLiveData<List<Standing>>()
    val standings: LiveData<List<Standing>> = _standings

    init {
        getStandings()
    }

    private fun getStandings(){
        viewModelScope.launch(Dispatchers.IO) {
            val answer = StatsBuilder.statsService.getStandings(PREMIER_LEAGUE_ID, SEASON)
            _standings.postValue(answer.response.first().league.standings.first())
        }
    }
}