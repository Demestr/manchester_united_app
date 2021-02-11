/*
 * Created by Dmitry Lipski on 11.02.21 15:35
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 15:02
 */

package com.lipssoftware.manchester.united.ui.standings

import com.lipssoftware.manchester.united.data.repository.StandingsRepository
import com.lipssoftware.manchester.united.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class StandingsPresenter @Inject constructor(private val repository: StandingsRepository) :
    BasePresenter<StandingsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showLoading(true)
        compositeDisposable.add(repository
            .getStandings()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { viewState.showLoading(false) }
            .subscribe (
                {
                    viewState.loadStandings(it)
                },
                {viewState.showError(it.message ?: "Error")}
            ))
    }
}