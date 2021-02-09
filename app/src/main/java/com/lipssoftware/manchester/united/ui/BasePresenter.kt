/*
 * Created by Dmitry Lipski on 09.02.21 17:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09.02.21 14:36
 */

package com.lipssoftware.manchester.united.ui

interface BasePresenter<V : BaseView> {

    fun attachView(view: V)

    fun detachView()

    fun destroy()
}