/*
 * Created by Dmitry Lipski on 11.01.21 9:15
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 9:10
 */

package com.lipssoftware.manchester.united.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lipssoftware.manchester.united.databinding.FragmentStandingsBinding

class StandingsFragment : Fragment() {

    private lateinit var binding: FragmentStandingsBinding
    private val standingsViewModel by viewModels<StandingsViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentStandingsBinding.inflate(inflater)
        binding.listStandings.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        standingsViewModel.standings.observe(viewLifecycleOwner){ standings ->
            if (!standings.isNullOrEmpty())
                binding.listStandings.adapter = StandingsAdapter(standings)
        }
    }
}