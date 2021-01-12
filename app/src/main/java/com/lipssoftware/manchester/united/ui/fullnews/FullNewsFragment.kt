/*
 * Created by Dmitry Lipski on 12.01.21 16:56
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12.01.21 16:56
 */

package com.lipssoftware.manchester.united.ui.fullnews

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import coil.load
import com.lipssoftware.manchester.united.data.model.news.NewsDomain
import com.lipssoftware.manchester.united.databinding.FragmentFullNewsBinding
import com.lipssoftware.manchester.united.utils.getTextFromHtml

class FullNewsFragment : Fragment() {

    private lateinit var binding: FragmentFullNewsBinding
    private val viewModel by viewModels<FullNewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.supportPostponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFullNewsBinding.inflate(inflater)
        val news = arguments?.getParcelable<NewsDomain>("fullNews")
        news?.let {
            ViewCompat.setTransitionName(binding.txtTitleFullNews, "title_${it.id}")
            ViewCompat.setTransitionName(binding.imageFullNews, "image_${it.id}")
            ViewCompat.setTransitionName(binding.txtDateFullNews, "date_${it.id}")
            ViewCompat.setTransitionName(binding.txtTextFullNews, "body_${it.id}")
            viewModel.setNews(it)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.news.observe(viewLifecycleOwner) { news ->
            news?.let {
                binding.txtTitleFullNews.text = it.title
                binding.imageFullNews.load(it.imageUrl)
                binding.txtDateFullNews.text = it.pubDate
                binding.txtTextFullNews.text =
                    getTextFromHtml(it.text, Html.FROM_HTML_MODE_LEGACY)
            }
        }
    }
}