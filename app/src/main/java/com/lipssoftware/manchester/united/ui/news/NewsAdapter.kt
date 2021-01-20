/*
 * Created by Dmitry Lipski on 20.01.21 16:30
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 20.01.21 13:07
 */

package com.lipssoftware.manchester.united.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.databinding.ItemNewsBinding
import com.lipssoftware.manchester.united.utils.convertDateToString
import com.lipssoftware.manchester.united.utils.getTextFromHtml

class NewsAdapter(
    private val news: List<NewsDomain>,
    val onClick: (news: NewsDomain, extras: FragmentNavigator.Extras) -> Unit
) :
    RecyclerView.Adapter<NewsAdapter.StandingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingsViewHolder =
        StandingsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: StandingsViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount(): Int = news.size

    inner class StandingsViewHolder(private val item: ItemNewsBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(newsDomain: NewsDomain) {
            ViewCompat.setTransitionName(item.cslItemNews, "news_${newsDomain.id}")
            val extras = FragmentNavigatorExtras(
                item.cslItemNews to "news_${newsDomain.id}",
            )

            item.ivItemNewsPicture.load(newsDomain.imageUrl){
                crossfade(true)
                allowHardware(false)
            }
            item.tvItemNewsTitle.text = newsDomain.title
            item.tvItemNewsDate.text = convertDateToString(newsDomain.pubDate)
            item.tvItemNewsBody.text = getTextFromHtml(newsDomain.text)
            item.root.setOnClickListener { onClick(newsDomain, extras) }
        }
    }
}