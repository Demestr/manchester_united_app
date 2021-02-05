/*
 * Created by Dmitry Lipski on 05.02.21 14:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 05.02.21 9:45
 */

package com.lipssoftware.manchester.united.ui.news.fullnews

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.google.android.material.transition.MaterialSharedAxis
import com.lipssoftware.manchester.united.R
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.databinding.FragmentFullNewsBinding
import com.lipssoftware.manchester.united.utils.convertDateToString
import com.lipssoftware.manchester.united.utils.getTextFromHtml

class FullNewsFragment : Fragment() {

    private lateinit var binding: FragmentFullNewsBinding
    private val viewModel by viewModels<FullNewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.supportPostponeEnterTransition()
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.large_animation_duration).toLong()
        }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = resources.getInteger(R.integer.large_animation_duration).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFullNewsBinding.inflate(inflater)
        val news = arguments?.getParcelable<NewsDomain>("fullNews")
        news?.let { newsDomain ->
            ViewCompat.setTransitionName(binding.root, "news_${newsDomain.id}")
            viewModel.setNews(newsDomain)
            binding.btnOpenInBrowser.setOnClickListener {
                val newsIntent = Intent(Intent.ACTION_VIEW, Uri.parse(newsDomain.link))
                startActivity(newsIntent)
            }
        }
        with(binding.ibFullNewsBack){
            ObjectAnimator.ofFloat(this, "alpha", 1f).apply {
                duration = 500
                start()
            }
            setOnClickListener { activity?.onBackPressed() }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.news.observe(viewLifecycleOwner) { news ->
            news?.let {
                binding.tvFullNewsTitle.text = it.title
                binding.ivFullNewsPicture.load(it.imageUrl){ allowHardware(false) }
                binding.tvFullNewsDate.text = convertDateToString(it.pubDate)
                binding.tvFullNewsBody.text =
                    getTextFromHtml(it.text, Html.FROM_HTML_MODE_LEGACY)
            }
        }
    }
}