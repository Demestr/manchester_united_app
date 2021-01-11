/*
 * Created by Dmitry Lipski on 11.01.21 17:05
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 17:05
 */

package com.lipssoftware.manchester.united.ui.news

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lipssoftware.manchester.united.data.model.news.NewsItem
import com.lipssoftware.manchester.united.databinding.ItemNewsBinding

class NewsAdapter(private val standings: List<NewsItem>) :
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

    class StandingsViewHolder(private val item: ItemNewsBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bind(newsItem: NewsItem) {
            item.newsImage.load(newsItem.imageUrl)
            item.newsTitle.text = newsItem.title
            item.newsDate.text = newsItem.pubDate
            //val text = if (newsItem.text.length > 200) newsItem.text.take(200) + "...</p>" else newsItem.text
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                item.newsText.text = Html.fromHtml(newsItem.text, Html.FROM_HTML_MODE_COMPACT)
            else
                item.newsText.text = Html.fromHtml(newsItem.text)
        }
    }
}