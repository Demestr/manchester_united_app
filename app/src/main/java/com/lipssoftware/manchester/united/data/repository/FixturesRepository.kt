/*
 * Created by Dmitry Lipski on 25.01.21 13:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 25.01.21 10:53
 */

package com.lipssoftware.manchester.united.data.repository

import com.lipssoftware.manchester.united.data.database.FixturesDao
import com.lipssoftware.manchester.united.data.model.domain.MatchDomain
import com.lipssoftware.manchester.united.data.network.StatsService
import com.lipssoftware.manchester.united.utils.MAN_UTD_ID
import com.lipssoftware.manchester.united.utils.SEASON
import javax.inject.Inject

class FixturesRepository @Inject constructor(
    private val statsService: StatsService,
    private val fixturesDao: FixturesDao
    ): Repository {

    suspend fun getFixtures(): List<MatchDomain>{
        return fixturesDao.getFixtures()
    }

    suspend fun refreshFixtures(){
        try {
            val matches = statsService.getFixtures(MAN_UTD_ID, SEASON).response
            fixturesDao.insertFixtures(matches.map { it.toMatchDomain() })
        } catch (exception: Exception){
            println(exception.message)
        }
    }
}