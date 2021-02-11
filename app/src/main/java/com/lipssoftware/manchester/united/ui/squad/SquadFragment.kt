/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 14:43
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
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.lipssoftware.manchester.united.R
import com.lipssoftware.manchester.united.data.model.players.Player
import com.lipssoftware.manchester.united.databinding.FragmentSquadBinding
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class SquadFragment : MvpAppCompatFragment(), SquadView {

    @Inject
    lateinit var presenterProvider: Provider<SquadPresenter>
    private val squadPresenter by moxyPresenter { presenterProvider.get() }
    private lateinit var binding: FragmentSquadBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSquadBinding.inflate(inflater)
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.normal_animation_duration).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.normal_animation_duration).toLong()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
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
    }

    override fun loadSquad(squad: List<Player>) {
        binding.rvSquadList.adapter = SquadAdapter(squad) { profile, itemView ->
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

    override fun showLoading(isLoading: Boolean) {
        binding.rvSquadList.isVisible = !isLoading
        binding.pbSquad.isVisible = isLoading
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}