/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 13:37
 */

package com.lipssoftware.manchester.united.ui.standings

import com.lipssoftware.manchester.united.data.model.domain.StandingDomain
import com.lipssoftware.manchester.united.mvp.BaseView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface StandingsView : BaseView {

    @AddToEndSingle
    fun loadStandings(standings: List<StandingDomain>)
}