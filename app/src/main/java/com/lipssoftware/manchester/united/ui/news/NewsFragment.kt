/*
 * Created by Dmitry Lipski on 18.01.21 10:42
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 18.01.21 10:35
 */

package com.lipssoftware.manchester.united.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import com.lipssoftware.manchester.united.R
import com.lipssoftware.manchester.united.data.database.ManUtdDatabase
import com.lipssoftware.manchester.united.data.network.NewsBuilder
import com.lipssoftware.manchester.united.data.repository.NewsRepository
import com.lipssoftware.manchester.united.databinding.FragmentNewsBinding
import com.lipssoftware.manchester.united.utils.Status

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel by viewModels<NewsViewModel> {
        NewsViewModelFactory(
            NewsRepository(
                NewsBuilder.newsService,
                ManUtdDatabase.getInstance(requireContext()).getNewsDao()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater)
        binding.rvNewsList.apply {
            layoutManager = LinearLayoutManager(context)
        }
        binding.srlNews.setOnRefreshListener {
            newsViewModel.getNews()
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
            standings?.let { resource ->
                when (standings.status) {
                    Status.LOADING -> {
                        showUI(false)
                    }
                    Status.SUCCESS -> {
                        resource.data?.let { list ->
                            binding.rvNewsList.adapter = NewsAdapter(list) { news, extras ->
                                    exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
                                        duration = resources.getInteger(R.integer.large_animation_duration).toLong()
                                    }
                                    reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
                                        duration = resources.getInteger(R.integer.large_animation_duration).toLong()
                                    }
                                val bundle = Bundle().apply { putParcelable("fullNews", news) }
                                    findNavController().navigate(R.id.action_navigation_news_to_fullNewsFragment, bundle)
                                }
                        }
                        showUI()
                        binding.srlNews.isRefreshing = false
                    }
                    Status.ERROR -> {
                        showUI()
                        binding.srlNews.isRefreshing = false
                        Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun showUI(showUi: Boolean = true) {
        binding.rvNewsList.isVisible = showUi
        binding.pbNews.isVisible = !showUi
    }
}