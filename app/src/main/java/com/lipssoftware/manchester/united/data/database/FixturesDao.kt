/*
 * Created by Dmitry Lipski on 20.01.21 11:18
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 20.01.21 11:18
 */

package com.lipssoftware.manchester.united.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lipssoftware.manchester.united.data.model.fixtures.MatchDomain

@Dao
interface FixturesDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertFixtures(fixtures: List<MatchDomain>)

    @Query("SELECT * FROM fixtures")
    suspend fun getFixtures(): List<MatchDomain>
}