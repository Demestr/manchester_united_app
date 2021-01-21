/*
 * Created by Dmitry Lipski on 21.01.21 14:58
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 20.01.21 16:31
 */

package com.lipssoftware.manchester.united.data.model.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fixtures")
data class MatchDomain(
    @PrimaryKey val id: Int,
    val timestamp: Long,
    @ColumnInfo(name = "league_name") val leagueName: String,
    val referee: String?,
    @ColumnInfo(name = "home_team_name") val homeTeamName: String,
    @ColumnInfo(name = "home_team_logo") val homeTeamLogo: String,
    @ColumnInfo(name = "away_team_name") val awayTeamName: String,
    @ColumnInfo(name = "away_team_logo") val awayTeamLogo: String,
    @ColumnInfo(name = "home_team_goals")val homeTeamGoals: Int?,
    @ColumnInfo(name = "away_team_goals")val awayTeamGoals: Int?,
    @ColumnInfo(name = "venue_name") val venueName: String,
    @ColumnInfo(name = "venue_city") val venueCity: String,
    @ColumnInfo(name = "status_short") val statusShort: String,
    @ColumnInfo(name = "status_long") val statusLong: String
)
