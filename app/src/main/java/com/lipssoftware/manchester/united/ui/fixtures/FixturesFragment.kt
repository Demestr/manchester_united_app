/*
 * Created by Dmitry Lipski on 19.01.21 16:24
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 19.01.21 16:24
 */

package com.lipssoftware.manchester.united.ui.fixtures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.lipssoftware.manchester.united.data.network.StatsBuilder
import com.lipssoftware.manchester.united.data.repository.FixturesRepository
import com.lipssoftware.manchester.united.databinding.FragmentFixturesBinding
import com.lipssoftware.manchester.united.utils.Status

class FixturesFragment : Fragment() {

    private lateinit var binding: FragmentFixturesBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var snapHelper: SnapHelper
    private var isPositioned: Boolean = false
    private val fixturesViewModel by viewModels<FixturesViewModel>{ FixturesViewModelFactory(
        FixturesRepository(StatsBuilder.statsService)) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentFixturesBinding.inflate(inflater)
        layoutManager = FixturesAdapter.ProminentLayoutManager(requireContext())
        snapHelper = PagerSnapHelper()
        binding.rvFixturesList.apply {
            setItemViewCacheSize(4)
            layoutManager = this@FixturesFragment.layoutManager
            addItemDecoration(FixturesAdapter.BoundsOffsetDecoration())
            setHasFixedSize(true)
        }
        snapHelper.attachToRecyclerView(binding.rvFixturesList)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fixturesViewModel.standings.observe(viewLifecycleOwner){ standings ->
            standings?.let { resource ->
                when(standings.status){
                    Status.LOADING ->{
                        showUI(false)
                    }
                    Status.SUCCESS -> {
                        resource.data?.let { list ->
                            binding.rvFixturesList.adapter = FixturesAdapter(list)
                            val lastPlayedMatch = list.filter {
                                it.fixture.status.short == "FT" ||
                                it.fixture.status.short == "1H" ||
                                it.fixture.status.short == "HT" ||
                                it.fixture.status.short == "2H" ||
                                it.fixture.status.short == "ET" ||
                                it.fixture.status.short == "P" ||
                                it.fixture.status.short == "FT"
                            }.maxByOrNull { it.fixture.timestamp }
                            if(!isPositioned) initRecyclerViewPosition(list.indexOf(lastPlayedMatch))
                        }
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

    private fun initRecyclerViewPosition(position: Int) {

        layoutManager.scrollToPosition(position)

        binding.rvFixturesList.doOnPreDraw {
            val targetView = layoutManager.findViewByPosition(position) ?: return@doOnPreDraw
            val distanceToFinalSnap =
                snapHelper.calculateDistanceToFinalSnap(layoutManager, targetView)
                    ?: return@doOnPreDraw

            layoutManager.scrollToPositionWithOffset(position, -distanceToFinalSnap[1])
        }

        isPositioned = true
    }

    private fun showUI(showUi: Boolean = true) {
        binding.rvFixturesList.isVisible = showUi
        binding.pbFixtures.isVisible = !showUi
    }
}