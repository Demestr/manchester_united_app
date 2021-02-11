/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 14:16
 */

package com.lipssoftware.manchester.united.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.lipssoftware.manchester.united.R
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.databinding.FragmentNewsBinding
import com.lipssoftware.manchester.united.ui.news.fullnews.FullNewsFragment.Companion.NEWS_ARG_KEY
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class NewsFragment : MvpAppCompatFragment(), NewsView {

    @Inject
    lateinit var presenterProvider: Provider<NewsPresenter>
    private val newsPresenter: NewsPresenter by moxyPresenter { presenterProvider.get() }
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater)
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.normal_animation_duration).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.normal_animation_duration).toLong()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        binding.rvNewsList.apply { layoutManager = LinearLayoutManager(context) }
        binding.srlNews.setOnRefreshListener { newsPresenter.onRefresh() }
        binding.rvNewsList.adapter = NewsAdapter { newsItem, itemView ->
            newsPresenter.onNewsClick(newsItem, itemView)
        }
    }

    override fun updateNews(news: List<NewsDomain>) {
        with(binding.rvNewsList) {
            (adapter as NewsAdapter).updateList(news) {
                val savedInstance = this.layoutManager?.onSaveInstanceState()
                it.dispatchUpdatesTo(this.adapter as NewsAdapter)
                this.layoutManager?.onRestoreInstanceState(savedInstance)
            }
        }
    }

    override fun showIsUpdatedMessage(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    override fun openNewsDetail(newsDomain: NewsDomain, view: View) {
        val bundle = Bundle().apply { putParcelable(NEWS_ARG_KEY, newsDomain) }
        val extras = FragmentNavigatorExtras(
            view to "news_${newsDomain.pubDate}",
        )
        findNavController().navigate(
            R.id.action_navigation_news_to_fullNewsFragment,
            bundle,
            null,
            extras
        )
    }

    override fun showLoading(isLoading: Boolean) {
        binding.rvNewsList.isVisible = !isLoading
        binding.pbNews.isVisible = isLoading
        if (!isLoading) binding.srlNews.isRefreshing = false
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}