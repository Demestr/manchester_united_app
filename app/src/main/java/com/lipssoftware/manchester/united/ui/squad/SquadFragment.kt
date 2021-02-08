/*
 * Created by Dmitry Lipski on 08.02.21 14:09
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 08.02.21 10:36
 */

package com.lipssoftware.manchester.united.ui.squad

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
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.lipssoftware.manchester.united.R
import com.lipssoftware.manchester.united.databinding.FragmentSquadBinding
import com.lipssoftware.manchester.united.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SquadFragment : Fragment() {

    private lateinit var binding: FragmentSquadBinding
    private val squadViewModel by viewModels<SquadViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSquadBinding.inflate(inflater)
        binding.rvSquadList.apply {
            val itemDecoration = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                layoutManager = GridLayoutManager(context, 2)
                SquadAdapter.PlayerItemDecoration(
                    (8 * resources.displayMetrics.density).toInt(),
                    2,
                    true
                )
            }
            else {
                layoutManager = GridLayoutManager(context, 4)
                SquadAdapter.PlayerItemDecoration(
                    (8 * resources.displayMetrics.density).toInt(),
                    4,
                    true
                )
            }
            addItemDecoration(itemDecoration)
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        squadViewModel.players.observe(viewLifecycleOwner) { squad ->
            squad?.let { resource ->
                when (squad.status) {
                    Status.LOADING -> {
                        showUI(false)
                    }
                    Status.SUCCESS -> {
                        resource.data?.let { list ->
                            binding.rvSquadList.adapter = SquadAdapter(list) { profile, itemView ->
                                exitTransition = MaterialElevationScale(false).apply {
                                    duration =
                                        resources.getInteger(R.integer.normal_animation_duration)
                                            .toLong()
                                }
                                reenterTransition = MaterialElevationScale(true).apply {
                                    duration =
                                        resources.getInteger(R.integer.normal_animation_duration)
                                            .toLong()
                                }
                                val bundle = Bundle().apply {
                                    putParcelable("player", profile)
                                }
                                val extras = FragmentNavigatorExtras(
                                    itemView to "tn_${profile.number}",
                                )
                                findNavController().navigate(
                                    R.id.action_navigation_squad_to_navigation_player_profile,
                                    bundle,
                                    null,
                                    extras
                                )
                            }
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

    private fun showUI(showUi: Boolean = true) {
        binding.rvSquadList.isVisible = showUi
        binding.pbSquad.isVisible = !showUi
    }
}