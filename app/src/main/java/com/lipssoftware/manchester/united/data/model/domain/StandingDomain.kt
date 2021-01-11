/*
 * Created by Dmitry Lipski on 11.01.21 12:53
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 12:16
 */

package com.lipssoftware.manchester.united.data.model.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "standings")
data class StandingDomain(
    @Embedded val all: FormDomain,
    val description: String?,
    val form: String?,
    val goalsDiff: Int,
    val points: Int,
    @PrimaryKey val rank: Int,
    val status: String?,
    @Embedded val team: TeamDomain,
)
