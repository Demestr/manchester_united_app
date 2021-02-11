/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 13:58
 */

package com.lipssoftware.manchester.united.ui.fixtures

import com.lipssoftware.manchester.united.data.model.domain.MatchDomain
import com.lipssoftware.manchester.united.mvp.BaseView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface FixturesView: BaseView {

    @AddToEndSingle
    fun showFixtures(fixtures: List<MatchDomain>)

    @AddToEndSingle
    fun scrollToLastMatch()
}
