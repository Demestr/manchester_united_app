/*
 * Created by Dmitry Lipski on 27.01.21 16:14
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 27.01.21 11:34
 */

package com.lipssoftware.manchester.united.di

import android.content.Context
import com.lipssoftware.manchester.united.data.database.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ManUtdDatabase = ManUtdDatabase.getInstance(appContext)

    @Provides
    fun provideNewsDao(database: ManUtdDatabase): NewsDao = database.getNewsDao()

    @Provides
    fun provideStatsDao(database: ManUtdDatabase): FixturesDao = database.getFixturesDao()

    @Provides
    fun provideStandingsDao(database: ManUtdDatabase): StandingsDao = database.getStandingsDao()

    @Provides
    fun provideHelpersDao(database: ManUtdDatabase): HelperDao = database.getHelperDao()
}