/*
 * Created by Dmitry Lipski on 25.01.21 13:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 25.01.21 10:50
 */

package com.lipssoftware.manchester.united.di

import android.content.Context
import com.lipssoftware.manchester.united.data.database.FixturesDao
import com.lipssoftware.manchester.united.data.database.NewsDao
import com.lipssoftware.manchester.united.data.database.StandingsDao
import com.lipssoftware.manchester.united.data.network.NewsService
import com.lipssoftware.manchester.united.data.network.StatsService
import com.lipssoftware.manchester.united.data.repository.FixturesRepository
import com.lipssoftware.manchester.united.data.repository.NewsRepository
import com.lipssoftware.manchester.united.data.repository.SquadRepository
import com.lipssoftware.manchester.united.data.repository.StandingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(newsService: NewsService, newsDao: NewsDao): NewsRepository =
        NewsRepository(newsService, newsDao)

    @Provides
    @Singleton
    fun provideStandingsRepository(statsService: StatsService, standingsDao: StandingsDao): StandingsRepository =
        StandingsRepository(statsService, standingsDao)

    @Provides
    @Singleton
    fun provideFixturesRepository(statsService: StatsService, fixturesDao: FixturesDao): FixturesRepository =
        FixturesRepository(statsService, fixturesDao)

    @Provides
    @Singleton
    fun provideSquadRepository(@ApplicationContext appContext: Context): SquadRepository =
        SquadRepository(appContext)
}