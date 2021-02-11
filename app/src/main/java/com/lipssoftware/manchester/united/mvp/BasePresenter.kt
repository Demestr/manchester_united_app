/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 13:37
 */

package com.lipssoftware.manchester.united.mvp

import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter

abstract class BasePresenter<V: BaseView>: MvpPresenter<V>() {

    val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}