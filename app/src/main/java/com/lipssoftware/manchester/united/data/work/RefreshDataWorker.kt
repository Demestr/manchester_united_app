/*
 * Created by Dmitry Lipski on 26.01.21 16:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 26.01.21 16:06
 */

package com.lipssoftware.manchester.united.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lipssoftware.manchester.united.data.repository.FixturesRepository
import com.lipssoftware.manchester.united.data.repository.StandingsRepository
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException

class RefreshDataWorker (
    ctx: Context,
    params: WorkerParameters,
    private val standingsRepository: StandingsRepository,
    private val fixturesRepository: FixturesRepository): CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            standingsRepository.refreshStandings()
            fixturesRepository.refreshFixtures()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "com.lipssoftware.manchester.united.data.work.RefreshDataWorker"
    }
}