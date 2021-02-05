/*
 * Created by Dmitry Lipski on 05.02.21 14:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 05.02.21 10:23
 */

package com.lipssoftware.manchester.united.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import com.lipssoftware.manchester.united.R
import com.lipssoftware.manchester.united.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel by viewModels<NewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater)
        context ?: return binding.root
        binding.rvNewsList.apply {
            layoutManager = LinearLayoutManager(context)
        }
        binding.srlNews.setOnRefreshListener {
            newsViewModel.fetchNewsFromRemote()
        }
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.large_animation_duration).toLong()
        }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.large_animation_duration).toLong()
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
        newsViewModel.news.observe(viewLifecycleOwner) { standings ->
            standings?.let { list ->
                binding.rvNewsList.adapter = NewsAdapter(list) { news, itemView ->
                    val bundle = Bundle().apply { putParcelable("fullNews", news) }
                    val extras = FragmentNavigatorExtras(
                        itemView to "news_${news.id}",
                    )
                    findNavController().navigate(
                        R.id.action_navigation_news_to_fullNewsFragment,
                        bundle,
                        null,
                        extras
                    )
                }
            }
            showUI()
        }
    }

    private fun showUI(showUi: Boolean = true) {
        binding.rvNewsList.isVisible = showUi
        binding.pbNews.isVisible = !showUi
        if (showUi) binding.srlNews.isRefreshing = false
    }
}