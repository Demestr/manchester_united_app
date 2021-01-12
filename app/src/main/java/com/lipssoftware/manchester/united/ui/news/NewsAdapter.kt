/*
 * Created by Dmitry Lipski on 12.01.21 16:56
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12.01.21 15:42
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
            ViewCompat.setTransitionName(item.newsTitle, "title_${newsDomain.id}")
            ViewCompat.setTransitionName(item.newsImage, "image_${newsDomain.id}")
            ViewCompat.setTransitionName(item.newsDate, "date_${newsDomain.id}")
            ViewCompat.setTransitionName(item.newsText, "body_${newsDomain.id}")
            val extras = FragmentNavigatorExtras(
                item.newsTitle to "title_${newsDomain.id}",
                item.newsImage to "image_${newsDomain.id}",
                item.newsDate to "date_${newsDomain.id}",
                item.newsText to "body_${newsDomain.id}",
            )

            item.newsImage.load(newsDomain.imageUrl)
            item.newsTitle.text = newsDomain.title
            item.newsDate.text = newsDomain.pubDate
            item.newsText.text = getTextFromHtml(newsDomain.text)
            item.root.setOnClickListener { onClick(newsDomain, extras) }
        }
    }
}