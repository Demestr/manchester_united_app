/*
 * Created by Dmitry Lipski on 25.01.21 13:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 25.01.21 10:53
 */

package com.lipssoftware.manchester.united.data.repository

import com.lipssoftware.manchester.united.data.database.StandingsDao
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain
import com.lipssoftware.manchester.united.data.network.StatsService
import com.lipssoftware.manchester.united.utils.PREMIER_LEAGUE_ID
import com.lipssoftware.manchester.united.utils.SEASON
import javax.inject.Inject

class StandingsRepository @Inject constructor(
    private val statsService: StatsService,
    private val standingsDao: StandingsDao
    ): Repository {

    suspend fun getStandings(): List<StandingDomain>{
        return standingsDao.getStandings()
    }

    suspend fun refreshStandings() {
        try {
            val result = statsService.getStandings(PREMIER_LEAGUE_ID, SEASON).response.firstOrNull()
            result?.league?.standings?.let { list ->
                val listDomain = list.first().map {
                    it.toStandingDomain()
                }
                standingsDao.insertStandings(listDomain) }
        }
        catch (exception: Exception){
            println(exception.message)
        }
    }

}