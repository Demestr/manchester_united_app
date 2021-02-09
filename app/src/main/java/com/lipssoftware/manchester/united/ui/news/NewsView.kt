/*
 * Created by Dmitry Lipski on 09.02.21 17:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09.02.21 17:05
 */

package com.lipssoftware.manchester.united.ui.news

import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.ui.BaseView

interface NewsView : BaseView {

    fun loadNews(news: List<NewsDomain>)

    fun showProgress(isProgress: Boolean)

    fun showError(message: String)
}