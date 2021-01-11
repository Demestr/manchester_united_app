/*
 * Created by Dmitry Lipski on 11.01.21 12:53
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 12:53
 */

package com.lipssoftware.manchester.united.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain

@Database(entities = [StandingDomain::class], version = 1, exportSchema = false)
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