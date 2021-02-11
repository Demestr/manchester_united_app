/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 10:15
 */

package com.lipssoftware.manchester.united.data.repository

import android.annotation.SuppressLint
import com.lipssoftware.manchester.united.data.database.FixturesDao
import com.lipssoftware.manchester.united.data.model.domain.MatchDomain
import com.lipssoftware.manchester.united.data.network.StatsService
import com.lipssoftware.manchester.united.utils.MAN_UTD_ID
import com.lipssoftware.manchester.united.utils.SEASON
import io.reactivex.Observable
import javax.inject.Inject

class FixturesRepository @Inject constructor(
    private val statsService: StatsService,
    private val fixturesDao: FixturesDao
    ): Repository {

    fun getFixtures(): Observable<List<MatchDomain>> = fixturesDao.getFixtures()


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