/*
 * Created by Dmitry Lipski on 27.01.21 16:14
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 27.01.21 15:45
 */

package com.lipssoftware.manchester.united.data.work

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lipssoftware.manchester.united.data.database.HelperDao
import com.lipssoftware.manchester.united.data.model.UpdateHelper
import com.lipssoftware.manchester.united.data.repository.FixturesRepository
import com.lipssoftware.manchester.united.data.repository.StandingsRepository
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.util.*
import java.util.concurrent.TimeUnit

class RefreshDataWorker @WorkerInject constructor (
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val standingsRepository: StandingsRepository,
    private val fixturesRepository: FixturesRepository,
    private val helperDao: HelperDao): CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val updateHelper = helperDao.lastUpdateTime()
            if (updateHelper == null || TimeUnit.MILLISECONDS.toHours(Calendar.getInstance().timeInMillis - updateHelper.lastFixturesAndStandingsUpdateTime) > 2) {
                standingsRepository.refreshStandings()
                fixturesRepository.refreshFixtures()
                helperDao.updateTime(UpdateHelper(UpdateHelper.ID, Calendar.getInstance().timeInMillis))
            }
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "com.lipssoftware.manchester.united.data.work.RefreshDataWorker"
    }
}