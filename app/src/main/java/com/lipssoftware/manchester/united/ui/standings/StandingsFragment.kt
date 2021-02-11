/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10.02.21 15:13
 */

package com.lipssoftware.manchester.united.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain
import com.lipssoftware.manchester.united.databinding.FragmentStandingsBinding
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class StandingsFragment : MvpAppCompatFragment(), StandingsView {

    @Inject
    lateinit var presenterProvider: Provider<StandingsPresenter>
    private val standingsPresenter: StandingsPresenter by moxyPresenter { presenterProvider.get() }
    private lateinit var binding: FragmentStandingsBinding


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentStandingsBinding.inflate(inflater)
        binding.rvStandingsTeamsList.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun loadStandings(standings: List<StandingDomain>) {
        binding.rvStandingsTeamsList.adapter = StandingsAdapter(standings)
    }

    override fun showLoading(isLoading: Boolean) {
        binding.rvStandingsTeamsList.isVisible = !isLoading
        binding.pbStandings.isVisible = isLoading
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}