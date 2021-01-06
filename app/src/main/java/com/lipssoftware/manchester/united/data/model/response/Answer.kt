/*
 * Created by Dmitry Lipski on 06.01.21 11:19
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 05.01.21 16:55
 */

package com.lipssoftware.manchester.united.data.model.response

data class Answer<T>(
    val errors: List<Error>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<T>,
    val results: Int
)