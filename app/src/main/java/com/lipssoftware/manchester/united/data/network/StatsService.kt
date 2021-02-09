/*
 * Created by Dmitry Lipski on 09.02.21 17:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 08.02.21 14:54
 */

package com.lipssoftware.manchester.united.data.network

import com.lipssoftware.manchester.united.data.model.fixtures.Match
import com.lipssoftware.manchester.united.data.model.response.Answer
import com.lipssoftware.manchester.united.data.model.standings.LeagueResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface StatsService {

    @GET("standings")
    fun getStandings(@Query("league") leagueId: Int, @Query("season") season: Int): Single<Answer<LeagueResponse>>

    @GET("fixtures")
    fun getFixtures(@Query("team") teamId: Int, @Query("season") season: Int): Single<Answer<Match>>
}