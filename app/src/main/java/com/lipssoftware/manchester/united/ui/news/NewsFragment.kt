/*
 * Created by Dmitry Lipski on 11.01.21 17:05
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 17:05
 */

package com.lipssoftware.manchester.united.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lipssoftware.manchester.united.data.network.NewsBuilder
import com.lipssoftware.manchester.united.data.repository.NewsRepository
import com.lipssoftware.manchester.united.databinding.FragmentNewsBinding
import com.lipssoftware.manchester.united.utils.Status

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel by viewModels<NewsViewModel>{ NewsViewModelFactory(NewsRepository(NewsBuilder.newsService)) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater)
        binding.listNews.apply {
            layoutManager = LinearLayoutManager(context)
        }
        newsViewModel.getNews()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsViewModel.news.observe(viewLifecycleOwner){ standings ->
            standings?.let { resource ->
                when(standings.status){
                    Status.LOADING ->{
                        binding.listNews.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        resource.data?.let { binding.listNews.adapter = NewsAdapter(it) }
                        binding.listNews.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.ERROR ->{
                        binding.listNews.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}