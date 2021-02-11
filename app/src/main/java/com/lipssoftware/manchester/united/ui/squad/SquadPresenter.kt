/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 13:37
 */

package com.lipssoftware.manchester.united.ui.squad

import com.lipssoftware.manchester.united.data.repository.SquadRepository
import com.lipssoftware.manchester.united.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SquadPresenter @Inject constructor(private val repository: SquadRepository) : BasePresenter<SquadView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getSquad()
    }

    private fun getSquad() {
        viewState.showLoading(true)
        compositeDisposable.add(repository.getSquad()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.loadSquad(it)
                viewState.showLoading(false)
            },
                { viewState.showError(it.message ?: "Error") }))
    }
}