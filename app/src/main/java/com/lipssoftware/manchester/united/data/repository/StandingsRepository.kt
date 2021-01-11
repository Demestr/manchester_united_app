/*
 * Created by Dmitry Lipski on 11.01.21 12:53
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 12:50
 */

package com.lipssoftware.manchester.united.data.repository

import com.lipssoftware.manchester.united.data.database.StandingsDao
import com.lipssoftware.manchester.united.data.model.domain.FormDomain
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain
import com.lipssoftware.manchester.united.data.model.domain.TeamDomain
import com.lipssoftware.manchester.united.data.network.StatsService
import com.lipssoftware.manchester.united.utils.PREMIER_LEAGUE_ID
import com.lipssoftware.manchester.united.utils.SEASON

class StandingsRepository(
    private val statsService: StatsService,
    private val standingsDao: StandingsDao
    ): Repository {

    suspend fun getStandings(): List<StandingDomain>{
        refreshStandings()
        return standingsDao.getStandings()
    }

    private suspend fun refreshStandings() {
        try {
            val result = statsService.getStandings(PREMIER_LEAGUE_ID, SEASON).response.firstOrNull()
            result?.league?.standings?.let { list ->
                val listDomain = list.first().map {
                    StandingDomain(
                        FormDomain(it.all.draw, it.all.goals.`for`, it.all.goals.against, it.all.lose, it.all.played, it.all.win),
                        it.description,
                        it.form,
                        it.goalsDiff,
                        it.points,
                        it.rank,
                        it.status,
                        TeamDomain(it.team.id, it.team.logo, it.team.name)
                    )
                }
                standingsDao.insertStandings(listDomain) }
        }
        catch (exception: Exception){
            println(exception.message)
        }
    }

}