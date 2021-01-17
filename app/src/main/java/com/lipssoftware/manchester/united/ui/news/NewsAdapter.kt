/*
 * Created by Dmitry Lipski on 13.01.21 10:45
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 13.01.21 8:59
 */

package com.lipssoftware.manchester.united.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lipssoftware.manchester.united.data.model.news.NewsDomain
import com.lipssoftware.manchester.united.databinding.ItemNewsBinding
import com.lipssoftware.manchester.united.utils.convertDateToString
import com.lipssoftware.manchester.united.utils.getTextFromHtml

class NewsAdapter(
    private val standings: List<NewsDomain>,
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
        holder.bind(standings[position])
    }

    override fun getItemCount(): Int = standings.size

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