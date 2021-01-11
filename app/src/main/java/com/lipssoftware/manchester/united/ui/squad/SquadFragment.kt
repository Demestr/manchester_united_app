/*
 * Created by Dmitry Lipski on 11.01.21 17:08
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 06.01.21 11:28
 */

package com.lipssoftware.manchester.united.ui.squad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.lipssoftware.manchester.united.R

class SquadFragment : Fragment() {

    private val squadViewModel by viewModels<SquadViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_squad, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        squadViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}