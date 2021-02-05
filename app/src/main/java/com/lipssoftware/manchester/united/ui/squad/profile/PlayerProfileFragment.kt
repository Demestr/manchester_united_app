/*
 * Created by Dmitry Lipski on 05.02.21 14:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 05.02.21 9:45
 */

package com.lipssoftware.manchester.united.ui.squad.profile

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.transition.MaterialContainerTransform
import com.lipssoftware.manchester.united.R
import com.lipssoftware.manchester.united.data.model.players.Player
import com.lipssoftware.manchester.united.databinding.FragmentPlayerProfileBinding
import com.lipssoftware.manchester.united.utils.getNumberPic

class PlayerProfileFragment : Fragment() {

    private lateinit var binding: FragmentPlayerProfileBinding
    private val viewModel by viewModels<PlayerProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.supportPostponeEnterTransition()
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            duration = resources.getInteger(R.integer.normal_animation_duration).toLong()
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
            ViewCompat.setTransitionName(binding.root, "tn_${player.number}")
            viewModel.setPlayer(player)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.player.observe(viewLifecycleOwner) { player ->
            player?.let {
                binding.ivPlayerPicture.setImageBitmap(BitmapFactory.decodeStream(requireContext().assets.open("players_photos/${it.photo}")))
                if(it.number / 10 == 0){
                    binding.ivFirstNumber.setImageDrawable(ContextCompat.getDrawable(requireContext(), getNumberPic(it.number)))
                    binding.ivSecondNumber.isVisible = false
                }
                else{
                    binding.ivFirstNumber.setImageDrawable(ContextCompat.getDrawable(requireContext(), getNumberPic(it.number / 10)))
                    binding.ivSecondNumber.setImageDrawable(ContextCompat.getDrawable(requireContext(), getNumberPic( it.number % 10)))
                }

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