/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 13:37
 */

package com.lipssoftware.manchester.united.mvp

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface BaseView: MvpView {

    @AddToEndSingle
    fun showLoading(isLoading: Boolean)

    @OneExecution
    fun showError(message: String)
}