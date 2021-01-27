/*
 * Created by Dmitry Lipski on 27.01.21 16:14
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 27.01.21 11:14
 */

package com.lipssoftware.manchester.united.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "update_info_table")
data class UpdateHelper(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "last_update_time") val lastFixturesAndStandingsUpdateTime: Long
){
    companion object{
        const val ID = "update_time"
    }
}
