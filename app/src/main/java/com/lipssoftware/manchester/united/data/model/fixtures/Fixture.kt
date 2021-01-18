/*
 * Created by Dmitry Lipski on 18.01.21 15:32
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 18.01.21 13:33
 */

package com.lipssoftware.manchester.united.data.model.fixtures

data class Fixture(
    val id: Int,
    val referee: String?,
    val timezone: String,
    val date: String,
    val timestamp: Long,
    val periods: Periods,
    val venue: Venue,
    val status: Status
)
