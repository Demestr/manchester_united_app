/*
 * Created by Dmitry Lipski on 20.01.21 11:18
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 20.01.21 10:26
 */

package com.lipssoftware.manchester.united.data.model.fixtures

import com.lipssoftware.manchester.united.data.model.common.League

data class Match(
    val fixture: Fixture,
    val league: League,
    val teams: Teams,
    val goals: Goals,
    val score: Score
){
    fun toMatchDomain(): MatchDomain{
        return MatchDomain(
            id = fixture.id,
            timestamp = fixture.timestamp,
            leagueName = league.name,
            referee = fixture.referee,
            homeTeamName = teams.home.name,
            homeTeamLogo = teams.home.logo,
            homeTeamGoals = goals.home,
            awayTeamName = teams.away.name,
            awayTeamLogo = teams.away.logo,
            awayTeamGoals = goals.away,
            venueName = fixture.venue.name,
            venueCity = fixture.venue.city,
            statusShort = fixture.status.short,
            statusLong = fixture.status.long
        )
    }
}
