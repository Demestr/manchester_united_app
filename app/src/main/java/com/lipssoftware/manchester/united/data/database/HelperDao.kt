/*
 * Created by Dmitry Lipski on 27.01.21 16:14
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 27.01.21 16:14
 */

package com.lipssoftware.manchester.united.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lipssoftware.manchester.united.data.model.UpdateHelper

@Dao
interface HelperDao {

    @Query("SELECT * FROM update_info_table WHERE id = :id")
    fun lastUpdateTime(id: String = UpdateHelper.ID): UpdateHelper?

    @Insert(onConflict = REPLACE)
    fun updateTime(updateHelper: UpdateHelper)
}