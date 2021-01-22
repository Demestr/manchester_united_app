/*
 * Created by Dmitry Lipski on 22.01.21 12:30
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 22.01.21 12:30
 */

package com.lipssoftware.manchester.united.data.work

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.lipssoftware.manchester.united.R
import com.lipssoftware.manchester.united.data.database.ManUtdDatabase
import com.lipssoftware.manchester.united.data.network.NewsBuilder
import com.lipssoftware.manchester.united.data.repository.NewsRepository
import com.lipssoftware.manchester.united.ui.fullnews.FullNewsFragmentArgs
import com.lipssoftware.manchester.united.utils.FOREGROUND_SERVICE_CHANNEL_ID
import com.lipssoftware.manchester.united.utils.NEWS_CHANNEL_ID
import com.lipssoftware.manchester.united.utils.getTextFromHtml
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException

class RefreshNewsWorker(private val ctx: Context, params: WorkerParameters) :
    CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result = coroutineScope {
        //Log.d("WORK_DEBUG", "Начало работы воркера!")
        val serviceNotification = NotificationCompat.Builder(ctx, FOREGROUND_SERVICE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_devil_48dp)
            .setCategory(Notification.CATEGORY_SERVICE).build()
        setForegroundAsync(ForegroundInfo(0, serviceNotification))
        val db = ManUtdDatabase.getInstance(ctx)
        val newsRepo = NewsRepository(NewsBuilder.newsService, db.getNewsDao())
        try {
            //Log.d("WORK_DEBUG", "Попытка получить последние новости посредством воркера!")
            newsRepo.refreshNews {
                it?.let {
                    val args = FullNewsFragmentArgs(it).toBundle()
                    val pendingIntent = NavDeepLinkBuilder(ctx)
                        .setGraph(R.navigation.news)
                        .setDestination(R.id.navigation_fullnews)
                        .setArguments(args)
                        .createPendingIntent()
                    val builder = NotificationCompat.Builder(ctx, NEWS_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_devil_48dp)
                        .setContentTitle(it.category)
                        .setContentText(it.title)
                        .setStyle(
                            NotificationCompat.BigTextStyle().bigText(getTextFromHtml(it.text))
                        )
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    with(NotificationManagerCompat.from(ctx)) {
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