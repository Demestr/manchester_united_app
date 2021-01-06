/*
 * Created by Dmitry Lipski on 05.01.21 12:47
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 05.01.21 12:47
 */

package com.lipssoftware.manchester.united.data.network

import com.lipssoftware.manchester.united.data.model.response.Answer
import com.lipssoftware.manchester.united.data.model.standings.LeagueResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StatsService {

    @GET("standings")
    suspend fun getStandings(@Query("league") leagueId: Int, @Query("season") season: Int): Answer<LeagueResponse>
}