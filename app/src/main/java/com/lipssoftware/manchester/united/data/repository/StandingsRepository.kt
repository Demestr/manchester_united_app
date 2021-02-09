/*
 * Created by Dmitry Lipski on 09.02.21 17:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09.02.21 9:36
 */

package com.lipssoftware.manchester.united.data.repository

import android.annotation.SuppressLint
import com.lipssoftware.manchester.united.data.database.StandingsDao
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain
import com.lipssoftware.manchester.united.data.network.StatsService
import com.lipssoftware.manchester.united.utils.PREMIER_LEAGUE_ID
import com.lipssoftware.manchester.united.utils.SEASON
import io.reactivex.Observable
import javax.inject.Inject

class StandingsRepository @Inject constructor(
    private val statsService: StatsService,
    private val standingsDao: StandingsDao
    ): Repository {

    fun getStandings(): Observable<List<StandingDomain>> {
        return standingsDao.getStandings()
    }

    @SuppressLint("CheckResult")
    fun refreshStandings() {
        try {
            statsService.getStandings(PREMIER_LEAGUE_ID, SEASON).subscribe { answer ->
                val result =  answer.response.firstOrNull()
                result?.league?.standings?.let { list ->
                    val listDomain = list.first().map {
                        it.toStandingDomain()
                    }
                    standingsDao.insertStandings(listDomain)
                }
            }
        }
        catch (exception: Exception){
            println(exception.message)
        }
    }

}