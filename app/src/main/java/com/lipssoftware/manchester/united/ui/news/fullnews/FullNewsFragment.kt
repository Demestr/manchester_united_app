/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 14:43
 */

package com.lipssoftware.manchester.united.ui.news.fullnews

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.transition.MaterialContainerTransform
import com.lipssoftware.manchester.united.R
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.databinding.FragmentFullNewsBinding
import com.lipssoftware.manchester.united.utils.convertDateToString
import com.lipssoftware.manchester.united.utils.getTextFromHtml
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullNewsFragment : Fragment() {

    private lateinit var binding: FragmentFullNewsBinding

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
        binding = FragmentFullNewsBinding.inflate(inflater)
        val news = arguments?.getParcelable<NewsDomain>(NEWS_ARG_KEY)
        news?.let { newsDomain ->
            ViewCompat.setTransitionName(binding.root, "news_${newsDomain.pubDate}")
            showNews(newsDomain)
            binding.btnOpenInBrowser.setOnClickListener {
                val newsIntent = Intent(Intent.ACTION_VIEW, Uri.parse(newsDomain.link))
                startActivity(newsIntent)
            }
        }
        with(binding.ibFullNewsBack) {
            ObjectAnimator.ofFloat(this, "alpha", 1f).apply {
                duration = 500
                start()
            }
            setOnClickListener { activity?.onBackPressed() }
        }
        return binding.root
    }


    private fun showNews(news: NewsDomain) {
        binding.tvFullNewsTitle.text = news.title
        binding.ivFullNewsPicture.load(news.imageUrl) { allowHardware(false) }
        binding.tvFullNewsDate.text = convertDateToString(news.pubDate)
        binding.tvFullNewsBody.text =
            getTextFromHtml(news.text, Html.FROM_HTML_MODE_LEGACY)
    }

    companion object {
        const val NEWS_ARG_KEY = "fullNews"
    }
}