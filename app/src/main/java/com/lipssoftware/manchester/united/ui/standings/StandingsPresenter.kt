/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 13:37
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
            .subscribe (
                {
                    viewState.loadStandings(it)
                    viewState.showLoading(false)
                },
                {viewState.showError(it.message ?: "Error")}
            ))
    }
}