/*
 * Created by Dmitry Lipski on 18.01.21 15:32
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 18.01.21 13:28
 */

package com.lipssoftware.manchester.united.data.model.common

data class Team(
    val id: Int,
    val logo: String,
    val name: String,
    val winner: Boolean?
)