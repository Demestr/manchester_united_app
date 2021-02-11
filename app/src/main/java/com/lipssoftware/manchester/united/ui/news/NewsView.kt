/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 13:54
 */

package com.lipssoftware.manchester.united.ui.news

import android.view.View
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.mvp.BaseView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface NewsView : BaseView {

    @AddToEndSingle
    fun updateNews(news: List<NewsDomain>)

    @OneExecution
    fun showIsUpdatedMessage(message: String)

    @OneExecution
    fun openNewsDetail(newsDomain: NewsDomain, view: View)
}