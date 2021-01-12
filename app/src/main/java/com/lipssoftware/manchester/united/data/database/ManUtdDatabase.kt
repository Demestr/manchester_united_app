/*
 * Created by Dmitry Lipski on 12.01.21 16:56
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12.01.21 8:44
 */

package com.lipssoftware.manchester.united.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain
import com.lipssoftware.manchester.united.data.model.news.NewsDomain

@Database(entities = [StandingDomain::class, NewsDomain::class], version = 1, exportSchema = false)
abstract class ManUtdDatabase : RoomDatabase() {

    abstract fun getNewsDao(): NewsDao
    abstract fun getStandingsDao(): StandingsDao

    companion object {

        private const val DATABASE_NAME = "manutd_database"

        // For Singleton instantiation
        @Volatile
        private var instance: ManUtdDatabase? = null

        fun getInstance(context: Context): ManUtdDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    ManUtdDatabase::class.java,
                    DATABASE_NAME
                )
                    .build().also { instance = it }
            }
        }
    }
}