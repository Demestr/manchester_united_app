/*
 * Created by Dmitry Lipski on 12.01.21 16:56
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12.01.21 16:49
 */

package com.lipssoftware.manchester.united.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lipssoftware.manchester.united.data.database.ManUtdDatabase
import com.lipssoftware.manchester.united.data.network.NewsBuilder
import com.lipssoftware.manchester.united.data.repository.NewsRepository
import com.lipssoftware.manchester.united.databinding.FragmentNewsBinding
import com.lipssoftware.manchester.united.utils.Status

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel by viewModels<NewsViewModel>{ NewsViewModelFactory(NewsRepository(NewsBuilder.newsService, ManUtdDatabase.getInstance(requireContext()).getNewsDao())) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater)
        binding.listNews.apply {
            layoutManager = LinearLayoutManager(context)
        }
        binding.refreshNews.setOnRefreshListener {
            newsViewModel.getNews()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsViewModel.news.observe(viewLifecycleOwner){ standings ->
            standings?.let { resource ->
                when(standings.status){
                    Status.LOADING ->{
                        showUI(false)
                    }
                    Status.SUCCESS -> {
                        resource.data?.let { list ->
                            binding.listNews.apply{
                                adapter = NewsAdapter(list) { news, extras ->
                                    findNavController()
                                        .navigate(NewsFragmentDirections
                                            .actionNavigationNewsToFullNewsFragment(news), extras) }
                                postponeEnterTransition()
                                viewTreeObserver.addOnPreDrawListener {
                                    startPostponedEnterTransition()
                                    true
                                }
                            }
                        }
                        showUI(true)
                        binding.refreshNews.isRefreshing = false
                    }
                    Status.ERROR ->{
                        showUI(true)
                        binding.refreshNews.isRefreshing = false
                        Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun showUI(showUi: Boolean) {
        binding.listNews.isVisible = showUi
        binding.progressBar.isVisible = !showUi
    }
}