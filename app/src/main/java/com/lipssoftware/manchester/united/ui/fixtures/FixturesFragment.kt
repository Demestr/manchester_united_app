/*
 * Created by Dmitry Lipski on 08.02.21 14:09
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 08.02.21 13:42
 */

package com.lipssoftware.manchester.united.ui.fixtures

import android.content.res.Configuration
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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.lipssoftware.manchester.united.databinding.FragmentFixturesBinding
import com.lipssoftware.manchester.united.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class FixturesFragment : Fragment() {

    private lateinit var binding: FragmentFixturesBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var snapHelper: SnapHelper
    private var isPositioned: Boolean = false
    private val fixturesViewModel by viewModels<FixturesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFixturesBinding.inflate(inflater)
        layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) FixturesAdapter.ProminentLayoutManager(
                requireContext(),
                desiredOrientation = LinearLayoutManager.VERTICAL
            ) else FixturesAdapter.ProminentLayoutManager(
                requireContext(),
                desiredOrientation = LinearLayoutManager.HORIZONTAL
            )
        snapHelper = PagerSnapHelper()
        binding.rvFixturesList.apply {
            setItemViewCacheSize(4)
            layoutManager = this@FixturesFragment.layoutManager
            val itemDecor = FixturesAdapter.BoundsOffsetDecoration(
                    isVertical = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            addItemDecoration(itemDecor)
            setHasFixedSize(true)
        }
        snapHelper.attachToRecyclerView(binding.rvFixturesList)
        binding.fabScrollToLastMatch.setOnClickListener {
            val direction =
                if ((binding.rvFixturesList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() < fixturesViewModel.indexOfLastPlayedMatch) 1 else -1
            binding.rvFixturesList.smoothScrollToPosition(fixturesViewModel.indexOfLastPlayedMatch + direction)
        }
        binding.rvFixturesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (abs((recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() - fixturesViewModel.indexOfLastPlayedMatch) > 1)
                    binding.fabScrollToLastMatch.show()
                else
                    binding.fabScrollToLastMatch.hide()
            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fixturesViewModel.standings.observe(viewLifecycleOwner) { standings ->
            standings?.let { resource ->
                when (standings.status) {
                    Status.LOADING -> {
                        showUI(false)
                    }
                    Status.SUCCESS -> {
                        resource.data?.let { list ->
                            binding.rvFixturesList.adapter = FixturesAdapter(list)
                            if (!isPositioned) initRecyclerViewPosition(fixturesViewModel.indexOfLastPlayedMatch)
                        }
                        showUI()
                    }
                    Status.ERROR -> {
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
            val distance =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                    -distanceToFinalSnap[1]
                else
                    -distanceToFinalSnap[0]
            layoutManager.scrollToPositionWithOffset(position, distance)
        }

        isPositioned = true
    }

    private fun showUI(showUi: Boolean = true) {
        binding.rvFixturesList.isVisible = showUi
        binding.pbFixtures.isVisible = !showUi
    }
}