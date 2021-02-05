/*
 * Created by Dmitry Lipski on 05.02.21 14:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 05.02.21 14:06
 */

package com.lipssoftware.manchester.united.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.databinding.ItemNewsBinding
import com.lipssoftware.manchester.united.utils.convertDateToString
import com.lipssoftware.manchester.united.utils.getTextFromHtml

class NewsAdapter(
    private val news: List<NewsDomain>,
    val onClick: (news: NewsDomain, itemView: View) -> Unit
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

        init{
            item.root.setOnClickListener { onClick(news[layoutPosition], it) }
        }

        fun bind(newsDomain: NewsDomain) {
            ViewCompat.setTransitionName(item.root, "news_${newsDomain.id}")
            item.ivItemNewsPicture.load(newsDomain.imageUrl){
                crossfade(true)
            }
            item.tvItemNewsTitle.text = newsDomain.title
            item.tvItemNewsDate.text = convertDateToString(newsDomain.pubDate)
            item.tvItemNewsBody.text = getTextFromHtml(newsDomain.text)
        }
    }
}