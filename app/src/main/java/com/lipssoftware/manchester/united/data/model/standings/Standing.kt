/*
 * Created by Dmitry Lipski on 06.01.21 11:19
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 06.01.21 11:19
 */

package com.lipssoftware.manchester.united.data.model.standings

import com.lipssoftware.manchester.united.data.model.common.Form
import com.lipssoftware.manchester.united.data.model.common.Team

data class Standing(
    val all: Form,
    val away: Form,
    val description: String,
    val form: String,
    val goalsDiff: Int,
    val group: String,
    val home: Form,
    val points: Int,
    val rank: Int,
    val status: String,
    val team: Team,
    val update: String
)