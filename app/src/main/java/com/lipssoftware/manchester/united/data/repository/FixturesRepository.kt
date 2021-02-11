/*
 * Created by Dmitry Lipski on 11.02.21 15:35
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 15:35
 */

package com.lipssoftware.manchester.united.data.repository

import android.annotation.SuppressLint
import com.lipssoftware.manchester.united.data.database.FixturesDao
import com.lipssoftware.manchester.united.data.network.StatsService
import com.lipssoftware.manchester.united.utils.MAN_UTD_ID
import com.lipssoftware.manchester.united.utils.SEASON
import javax.inject.Inject

class FixturesRepository @Inject constructor(
    private val statsService: StatsService,
    private val fixturesDao: FixturesDao
    ): Repository {

    fun getFixtures() = fixturesDao.getFixtures()


    @SuppressLint("CheckResult")
    fun refreshFixtures(){
        try {
            statsService.getFixtures(MAN_UTD_ID, SEASON).subscribe { answer ->
                fixturesDao.insertFixtures(answer.response.map { it.toMatchDomain() })
            }
        } catch (exception: Exception){
            println(exception.message)
        }
    }
}