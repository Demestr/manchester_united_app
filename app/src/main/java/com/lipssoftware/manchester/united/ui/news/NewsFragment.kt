/*
 * Created by Dmitry Lipski on 09.02.21 17:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09.02.21 16:40
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
import com.google.android.material.transition.MaterialSharedAxis
import com.lipssoftware.manchester.united.R
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.data.repository.NewsRepository
import com.lipssoftware.manchester.united.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : MvpAppCompatFragment(), NewsView {

    private lateinit var binding: FragmentNewsBinding

//    @Inject
//    lateinit var presenterProvider: Provider<NewsPresenter>

    @Inject
    lateinit var repo: NewsRepository

    private val newsPresenter: NewsPresenter by lazy { NewsPresenter(repo) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        newsPresenter.attachView(this)
        binding = FragmentNewsBinding.inflate(inflater)
        //context ?: return binding.root
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.normal_animation_duration).toLong()
        }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = resources.getInteger(R.integer.normal_animation_duration).toLong()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvNewsList.apply { layoutManager = LinearLayoutManager(context) }
        binding.srlNews.setOnRefreshListener { newsPresenter.onRefresh() }
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun loadNews(news: List<NewsDomain>) {
        binding.rvNewsList.adapter = NewsAdapter(news) { newsItem, itemView ->
            val bundle = Bundle().apply { putParcelable("fullNews", newsItem) }
            val extras = FragmentNavigatorExtras(
                itemView to "news_${newsItem.id}",
            )
            findNavController().navigate(
                R.id.action_navigation_news_to_fullNewsFragment,
                bundle,
                null,
                extras
            )
        }
    }

    override fun showProgress(isProgress: Boolean) {
        binding.rvNewsList.isVisible = !isProgress
        binding.pbNews.isVisible = isProgress
        if (!isProgress) binding.srlNews.isRefreshing = false
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsPresenter.detachView()
    }

    override fun onDetach() {
        super.onDetach()
        newsPresenter.destroy()
    }
}