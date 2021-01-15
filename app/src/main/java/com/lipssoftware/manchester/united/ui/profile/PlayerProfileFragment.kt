/*
 * Created by Dmitry Lipski on 15.01.21 17:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 15.01.21 17:09
 */

package com.lipssoftware.manchester.united.ui.profile

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.transition.MaterialContainerTransform
import com.lipssoftware.manchester.united.R
import com.lipssoftware.manchester.united.data.model.players.Player
import com.lipssoftware.manchester.united.databinding.FragmentPlayerProfileBinding

class PlayerProfileFragment : Fragment() {

    private lateinit var binding: FragmentPlayerProfileBinding
    private val viewModel by viewModels<PlayerProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.supportPostponeEnterTransition()
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 350
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerProfileBinding.inflate(inflater)
        val player = arguments?.getParcelable<Player>("player")
        player?.let {
            ViewCompat.setTransitionName(binding.root, player.birth.date)
            viewModel.setPlayer(player)
        }
        binding.ibPlayerBack.setOnClickListener { activity?.onBackPressed() }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.player.observe(viewLifecycleOwner) { player ->
            player?.let {
                binding.ivPlayerPicture.setImageBitmap(BitmapFactory.decodeStream(requireContext().assets.open("players_photos/${it.photo}")))
                binding.tvPlayerFirstName.text = it.firstname
                binding.tvPlayerLastName.text = it.lastname
                binding.tvPlayerDateBirth.text = it.birth.date
                binding.tvPlayerPosition.text = it.position
                binding.tvPlayerNationality.text = it.nationality
                binding.tvPlayerHeight.text = "${it.height} cm"
                binding.tvPlayerWeight.text = "${it.weight} kg"
            }
        }
    }
}