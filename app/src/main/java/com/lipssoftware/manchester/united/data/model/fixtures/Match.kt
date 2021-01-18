/*
 * Created by Dmitry Lipski on 18.01.21 15:32
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 18.01.21 13:41
 */

package com.lipssoftware.manchester.united.data.model.fixtures

import com.lipssoftware.manchester.united.data.model.common.League

data class Match(
    val fixture: Fixture,
    val league: League,
    val teams: Teams,
    val goals: Goals,
    val score: Score
)
