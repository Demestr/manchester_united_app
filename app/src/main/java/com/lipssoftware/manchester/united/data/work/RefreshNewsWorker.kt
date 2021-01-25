/*
 * Created by Dmitry Lipski on 25.01.21 13:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 25.01.21 13:09
 */

package com.lipssoftware.manchester.united.data.work

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.lipssoftware.manchester.united.R
import com.lipssoftware.manchester.united.data.repository.NewsRepository
import com.lipssoftware.manchester.united.ui.fullnews.FullNewsFragmentArgs
import com.lipssoftware.manchester.united.utils.FOREGROUND_SERVICE_CHANNEL_ID
import com.lipssoftware.manchester.united.utils.NEWS_CHANNEL_ID
import com.lipssoftware.manchester.united.utils.getTextFromHtml
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException

class RefreshNewsWorker @WorkerInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val newsRepository: NewsRepository) :
    CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result = coroutineScope {
        //Log.d("WORK_DEBUG", "Начало работы воркера!")
        val serviceNotification = NotificationCompat.Builder(applicationContext, FOREGROUND_SERVICE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_devil_48dp)
            .setCategory(Notification.CATEGORY_SERVICE).build()
        setForegroundAsync(ForegroundInfo(0, serviceNotification))
        try {
            //Log.d("WORK_DEBUG", "Попытка получить последние новости посредством воркера!")
            newsRepository.refreshNews {
                it?.let {
                    val args = FullNewsFragmentArgs(it).toBundle()
                    val pendingIntent = NavDeepLinkBuilder(applicationContext)
                        .setGraph(R.navigation.news)
                        .setDestination(R.id.navigation_fullnews)
                        .setArguments(args)
                        .createPendingIntent()
                    val builder = NotificationCompat.Builder(applicationContext, NEWS_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_devil_48dp)
                        .setContentTitle(it.category)
                        .setContentText(it.title)
                        .setStyle(
                            NotificationCompat.BigTextStyle().bigText(getTextFromHtml(it.text))
                        )
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    with(NotificationManagerCompat.from(applicationContext)) {
                        // notificationId is a unique int for each notification that you must define
                        notify(it.pubDate.toInt(), builder.build())
                    }
                }
            }

            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "com.lipssoftware.manchester.united.data.work.RefreshNewsWorker"
    }
}