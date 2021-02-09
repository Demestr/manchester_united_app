/*
 * Created by Dmitry Lipski on 09.02.21 17:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09.02.21 9:36
 */

package com.lipssoftware.manchester.united.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain
import io.reactivex.Observable

@Dao
interface StandingsDao {

    @Insert(onConflict = REPLACE)
    fun insertStandings(standings: List<StandingDomain>)

    @Query("SELECT * FROM standings")
    fun getStandings(): Observable<List<StandingDomain>>
}