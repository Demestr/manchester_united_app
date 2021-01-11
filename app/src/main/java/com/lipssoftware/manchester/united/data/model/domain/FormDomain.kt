/*
 * Created by Dmitry Lipski on 11.01.21 12:53
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 12:14
 */

package com.lipssoftware.manchester.united.data.model.domain

data class FormDomain(
    val draw: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val lose: Int,
    val played: Int,
    val win: Int
)
