/*
 * Created by Dmitry Lipski on 19.01.21 16:24
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 19.01.21 10:36
 */

package com.lipssoftware.manchester.united.data.repository

import com.lipssoftware.manchester.united.data.model.fixtures.Match
import com.lipssoftware.manchester.united.data.network.StatsService
import com.lipssoftware.manchester.united.utils.MAN_UTD_ID
import com.lipssoftware.manchester.united.utils.SEASON

class FixturesRepository(
    private val statsService: StatsService
    //private val standingsDao: StandingsDao
    ): Repository {

    suspend fun getFixtures(): List<Match>{
        return refreshFixtures()
    }

    private suspend fun refreshFixtures(): List<Match> {
        return try {
            statsService.getFixtures(MAN_UTD_ID, SEASON).response.sortedBy { it.fixture.timestamp }
        } catch (exception: Exception){
            println(exception.message)
            emptyList()
        }
    }
}