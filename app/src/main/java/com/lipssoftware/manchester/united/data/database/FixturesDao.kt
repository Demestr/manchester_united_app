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
import com.lipssoftware.manchester.united.data.model.domain.MatchDomain
import io.reactivex.Observable

@Dao
interface FixturesDao {

    @Insert(onConflict = REPLACE)
    fun insertFixtures(fixtures: List<MatchDomain>)

    @Query("SELECT * FROM fixtures")
    fun getFixtures(): Observable<List<MatchDomain>>
}