/*
 * Created by Dmitry Lipski on 12.02.21 15:28
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12.02.21 14:47
 */

package com.lipssoftware.manchester.united.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lipssoftware.manchester.united.data.model.UpdateHelper
import com.lipssoftware.manchester.united.data.model.domain.MatchDomain
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain

@Database(entities = [StandingDomain::class, NewsDomain::class, MatchDomain::class, UpdateHelper::class], version = 3, exportSchema = false)
abstract class ManUtdDatabase : RoomDatabase() {

    abstract fun getNewsDao(): NewsDao
    abstract fun getStandingsDao(): StandingsDao
    abstract fun getFixturesDao(): FixturesDao
    abstract fun getHelperDao(): HelperDao

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
                    .fallbackToDestructiveMigration()
                    .build().also { instance = it }
            }
        }
    }
}