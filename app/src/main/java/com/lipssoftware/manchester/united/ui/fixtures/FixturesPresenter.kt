/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 14:03
 */

package com.lipssoftware.manchester.united.ui.fixtures

import com.lipssoftware.manchester.united.data.model.domain.MatchDomain
import com.lipssoftware.manchester.united.data.repository.FixturesRepository
import com.lipssoftware.manchester.united.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FixturesPresenter @Inject constructor(private val repository: FixturesRepository) :
    BasePresenter<FixturesView>() {

    var indexOfLastPlayedMatch: Int = -1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getStandings()
    }

    fun goToLastMatchClick(){
        viewState.scrollToLastMatch()
    }

    private fun getStandings() {
        viewState.showLoading(true)
        compositeDisposable.add(
            repository.getFixtures()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ matches ->
                    findLastMatch(matches)
                    viewState.showFixtures(matches.sortedBy { it.timestamp })
                    viewState.showLoading(false)
                },
                    { viewState.showError(it.message ?: "Error") })
        )
    }

    private fun findLastMatch(matches: List<MatchDomain>) {
        val list = matches.sortedBy { it.timestamp }
        val lastPlayedMatch = list.filter {
            it.statusShort == "FT" ||
                    it.statusShort == "1H" ||
                    it.statusShort == "HT" ||
                    it.statusShort == "2H" ||
                    it.statusShort == "ET" ||
                    it.statusShort == "P" ||
                    it.statusShort == "AET"
        }.maxByOrNull { it.timestamp }
        indexOfLastPlayedMatch = list.indexOf(lastPlayedMatch)
    }
}