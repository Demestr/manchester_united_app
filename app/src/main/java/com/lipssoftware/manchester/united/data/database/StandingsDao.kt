/*
 * Created by Dmitry Lipski on 11.02.21 15:35
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 15:00
 */

package com.lipssoftware.manchester.united.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain
import io.reactivex.Single

@Dao
interface StandingsDao {

    @Insert(onConflict = REPLACE)
    fun insertStandings(standings: List<StandingDomain>)

    @Query("SELECT * FROM standings")
    fun getStandings(): Single<List<StandingDomain>>
}