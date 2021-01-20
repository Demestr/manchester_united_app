/*
 * Created by Dmitry Lipski on 20.01.21 16:30
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 20.01.21 15:50
 */

package com.lipssoftware.manchester.united.data.work

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lipssoftware.manchester.united.R
import com.lipssoftware.manchester.united.data.database.ManUtdDatabase
import com.lipssoftware.manchester.united.data.network.NewsBuilder
import com.lipssoftware.manchester.united.data.repository.NewsRepository
import com.lipssoftware.manchester.united.utils.NEWS_CHANNEL_ID
import com.lipssoftware.manchester.united.utils.getTextFromHtml
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException

class RefreshNewsWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result = coroutineScope {
        val db = ManUtdDatabase.getInstance(applicationContext)
        val newsRepo = NewsRepository(NewsBuilder.newsService, db.getNewsDao())
        try {
            newsRepo.refreshNews {
                it?.let {
                    val builder = NotificationCompat.Builder(applicationContext, NEWS_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_devil_48dp)
                        .setContentTitle(it.title)
                        .setStyle(NotificationCompat.BigTextStyle()
                            .bigText(getTextFromHtml(it.text)))
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