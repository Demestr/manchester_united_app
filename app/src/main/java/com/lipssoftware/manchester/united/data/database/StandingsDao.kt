/*
 * Created by Dmitry Lipski on 11.01.21 12:53
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 12:52
 */

package com.lipssoftware.manchester.united.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain

@Dao
interface StandingsDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertStandings(standings: List<StandingDomain>)

    @Query("SELECT * FROM standings")
    suspend fun getStandings(): List<StandingDomain>
}