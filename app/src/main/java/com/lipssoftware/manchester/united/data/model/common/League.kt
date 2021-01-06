/*
 * Created by Dmitry Lipski on 06.01.21 11:19
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 06.01.21 11:11
 */

package com.lipssoftware.manchester.united.data.model.common

import com.lipssoftware.manchester.united.data.model.standings.Standing

data class League(
    val country: String,
    val flag: String,
    val id: Int,
    val logo: String,
    val name: String,
    val season: Int,
    val standings: List<List<Standing>>
)