/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 14:43
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.lipssoftware.manchester.united.data.model.domain.MatchDomain
import com.lipssoftware.manchester.united.databinding.FragmentFixturesBinding
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider
import kotlin.math.abs

@AndroidEntryPoint
class FixturesFragment : MvpAppCompatFragment(), FixturesView {

    @Inject
    lateinit var presenterProvider: Provider<FixturesPresenter>
    private val fixturesPresenter by moxyPresenter { presenterProvider.get() }
    private lateinit var binding: FragmentFixturesBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var snapHelper: SnapHelper
    private var isPositioned: Boolean = false

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
                isVertical = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            )
            addItemDecoration(itemDecor)
            setHasFixedSize(true)
        }
        snapHelper.attachToRecyclerView(binding.rvFixturesList)
        binding.fabScrollToLastMatch.isVisible = abs((binding.rvFixturesList.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() - fixturesPresenter.indexOfLastPlayedMatch) > 1
        binding.fabScrollToLastMatch.setOnClickListener {
            fixturesPresenter.goToLastMatchClick()
        }
        binding.rvFixturesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (abs((recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() - fixturesPresenter.indexOfLastPlayedMatch) > 1)
                    binding.fabScrollToLastMatch.show()
                else
                    binding.fabScrollToLastMatch.hide()
            }
        })
        return binding.root
    }

    override fun showFixtures(fixtures: List<MatchDomain>) {
        binding.rvFixturesList.adapter = FixturesAdapter(fixtures)
        if (!isPositioned) initRecyclerViewPosition(fixturesPresenter.indexOfLastPlayedMatch)
    }

    override fun scrollToLastMatch() {
        val direction =
            if ((binding.rvFixturesList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() < fixturesPresenter.indexOfLastPlayedMatch) 1 else -1
        binding.rvFixturesList.smoothScrollToPosition(fixturesPresenter.indexOfLastPlayedMatch + direction)
    }

    override fun showLoading(isLoading: Boolean) {
        binding.rvFixturesList.isVisible = !isLoading
        binding.pbFixtures.isVisible = isLoading
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
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
}