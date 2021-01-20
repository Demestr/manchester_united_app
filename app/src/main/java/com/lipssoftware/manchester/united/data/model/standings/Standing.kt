/*
 * Created by Dmitry Lipski on 20.01.21 16:30
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 20.01.21 13:07
 */

package com.lipssoftware.manchester.united.data.model.standings

import com.lipssoftware.manchester.united.data.model.common.Form
import com.lipssoftware.manchester.united.data.model.common.Team
import com.lipssoftware.manchester.united.data.model.domain.FormDomain
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain
import com.lipssoftware.manchester.united.data.model.domain.TeamDomain

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
){

    fun toStandingDomain(): StandingDomain{
        return StandingDomain(
            FormDomain(all.draw, all.goals.`for`, all.goals.against, all.lose, all.played, all.win),
            description,
            form,
            goalsDiff,
            points,
            rank,
            status,
            TeamDomain(team.id, team.logo, team.name)
        )
    }
}