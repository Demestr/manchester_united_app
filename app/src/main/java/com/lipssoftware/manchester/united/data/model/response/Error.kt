/*
 * Created by Dmitry Lipski on 06.01.21 11:19
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 05.01.21 16:20
 */

package com.lipssoftware.manchester.united.data.model.response

data class Error(
    val time: String,
    val bug: String,
    val report: String
)
