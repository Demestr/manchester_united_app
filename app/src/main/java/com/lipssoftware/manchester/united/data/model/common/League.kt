/*
 * Created by Dmitry Lipski on 18.01.21 15:32
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 18.01.21 13:27
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
    val round: String?,
    val standings: List<List<Standing>>?
)