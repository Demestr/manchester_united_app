/*
 * Created by Dmitry Lipski on 20.01.21 16:30
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 20.01.21 15:26
 */

package com.lipssoftware.manchester.united

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.*
import com.lipssoftware.manchester.united.data.work.RefreshDataWorker
import com.lipssoftware.manchester.united.data.work.RefreshNewsWorker
import com.lipssoftware.manchester.united.utils.NEWS_CHANNEL_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ManUtdApplication: Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        setupRecurringWork()
        createNotificationChannel()
    }

    private fun setupRecurringWork() {
        applicationScope.launch {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }
                .build()
            val newsRequest = OneTimeWorkRequest.from(RefreshNewsWorker::class.java)
            val dataRequest = OneTimeWorkRequest.from(RefreshDataWorker::class.java)
            val repeatingDataRequest =
                PeriodicWorkRequestBuilder<RefreshDataWorker>(3, TimeUnit.HOURS)
                    .setConstraints(constraints)
                    .build()
            val repeatingNewsRequest =
                PeriodicWorkRequestBuilder<RefreshNewsWorker>(15, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build()
            WorkManager.getInstance(this@ManUtdApplication).apply {
                enqueueUniquePeriodicWork(
                    RefreshDataWorker.WORK_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,
                    repeatingDataRequest
                )
                enqueueUniquePeriodicWork(
                    RefreshNewsWorker.WORK_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,
                    repeatingNewsRequest
                )
                enqueue(listOf(newsRequest, dataRequest))
            }
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.resources.getString(R.string.channel_name)
            val descriptionText = applicationContext.resources.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NEWS_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}