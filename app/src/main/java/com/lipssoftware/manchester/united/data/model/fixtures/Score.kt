/*
 * Created by Dmitry Lipski on 18.01.21 15:32
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 18.01.21 13:34
 */

package com.lipssoftware.manchester.united.data.model.fixtures

data class Score(
    val halftime: Goals,
    val fulltime: Goals,
    val extratime: Goals,
    val penalty: Goals,
)
