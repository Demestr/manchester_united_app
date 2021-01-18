/*
 * Created by Dmitry Lipski on 18.01.21 15:32
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 18.01.21 13:44
 */

package com.lipssoftware.manchester.united.data.network

import com.lipssoftware.manchester.united.data.model.fixtures.Match
import com.lipssoftware.manchester.united.data.model.response.Answer
import com.lipssoftware.manchester.united.data.model.standings.LeagueResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StatsService {

    @GET("standings")
    suspend fun getStandings(@Query("league") leagueId: Int, @Query("season") season: Int): Answer<LeagueResponse>

    @GET("fixtures")
    suspend fun getFixtures(@Query("team") teamId: Int, @Query("season") season: Int): Answer<Match>
}