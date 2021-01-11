/*
 * Created by Dmitry Lipski on 11.01.21 12:53
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 11:26
 */

package com.lipssoftware.manchester.united.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lipssoftware.manchester.united.data.database.ManUtdDatabase
import com.lipssoftware.manchester.united.data.network.StatsBuilder
import com.lipssoftware.manchester.united.data.repository.StandingsRepository
import com.lipssoftware.manchester.united.databinding.FragmentStandingsBinding
import com.lipssoftware.manchester.united.utils.Status

class StandingsFragment : Fragment() {

    private lateinit var binding: FragmentStandingsBinding
    private val standingsViewModel by viewModels<StandingsViewModel>{ StandingsViewModelFactory(StandingsRepository(StatsBuilder.statsService, ManUtdDatabase.getInstance(requireContext()).getStandingsDao())) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentStandingsBinding.inflate(inflater)
        binding.listStandings.apply {
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
                        binding.listStandings.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        resource.data?.let { binding.listStandings.adapter = StandingsAdapter(it) }
                        binding.listStandings.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.ERROR ->{
                        binding.listStandings.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}