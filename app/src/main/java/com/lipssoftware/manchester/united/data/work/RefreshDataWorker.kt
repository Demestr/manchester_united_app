/*
 * Created by Dmitry Lipski on 20.01.21 16:30
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 20.01.21 13:48
 */

package com.lipssoftware.manchester.united.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lipssoftware.manchester.united.data.database.ManUtdDatabase
import com.lipssoftware.manchester.united.data.network.StatsBuilder.statsService
import com.lipssoftware.manchester.united.data.repository.FixturesRepository
import com.lipssoftware.manchester.united.data.repository.StandingsRepository
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException

class RefreshDataWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result = coroutineScope {
        val db = ManUtdDatabase.getInstance(applicationContext)
        val standingsRepo = StandingsRepository(statsService, db.getStandingsDao())
        val fixturesRepo = FixturesRepository(statsService, db.getFixturesDao())
        try {
            standingsRepo.refreshStandings()
            fixturesRepo.refreshFixtures()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "com.lipssoftware.manchester.united.data.work.RefreshDataWorker"
    }
}