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
            ViewCompat.setTransitionName(item.tvItemNewsTitle, "title_${newsDomain.id}")
            ViewCompat.setTransitionName(item.ivItemNewsPicture, "image_${newsDomain.id}")
            ViewCompat.setTransitionName(item.vItemNewsTitleBack, "back_${newsDomain.id}")
            ViewCompat.setTransitionName(item.tvItemNewsDate, "date_${newsDomain.id}")
            ViewCompat.setTransitionName(item.tvItemNewsBody, "body_${newsDomain.id}")
            val extras = FragmentNavigatorExtras(
                item.tvItemNewsTitle to "title_${newsDomain.id}",
                item.ivItemNewsPicture to "image_${newsDomain.id}",
                item.vItemNewsTitleBack to "back_${newsDomain.id}",
                item.tvItemNewsDate to "date_${newsDomain.id}",
                item.tvItemNewsBody to "body_${newsDomain.id}",
            )

            item.ivItemNewsPicture.load(newsDomain.imageUrl){
                crossfade(true)
            }
            item.tvItemNewsTitle.text = newsDomain.title
            item.tvItemNewsDate.text = convertDateToString(newsDomain.pubDate)
            item.tvItemNewsBody.text = getTextFromHtml(newsDomain.text)
            item.root.setOnClickListener { onClick(newsDomain, extras) }
        }
    }
}