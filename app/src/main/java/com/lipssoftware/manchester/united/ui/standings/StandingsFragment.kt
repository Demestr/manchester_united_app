/*
 * Created by Dmitry Lipski on 25.01.21 13:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 25.01.21 11:17
 */

package com.lipssoftware.manchester.united.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lipssoftware.manchester.united.databinding.FragmentStandingsBinding
import com.lipssoftware.manchester.united.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StandingsFragment : Fragment() {

    private lateinit var binding: FragmentStandingsBinding
    private val standingsViewModel by viewModels<StandingsViewModel>()

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        standingsViewModel.standings.observe(viewLifecycleOwner){ standings ->
            standings?.let { resource ->
                when(standings.status){
                    Status.LOADING ->{
                        showUI(false)
                    }
                    Status.SUCCESS -> {
                        resource.data?.let { binding.rvStandingsTeamsList.adapter = StandingsAdapter(it) }
                        showUI()
                    }
                    Status.ERROR ->{
                        showUI()
                        Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun showUI(showUi: Boolean = true) {
        binding.rvStandingsTeamsList.isVisible = showUi
        binding.pbStandings.isVisible = !showUi
    }
}