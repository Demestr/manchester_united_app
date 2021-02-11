/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10.02.21 13:08
 */

package com.lipssoftware.manchester.united.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.databinding.ItemNewsBinding
import com.lipssoftware.manchester.united.utils.convertDateToString
import com.lipssoftware.manchester.united.utils.getTextFromHtml

class NewsAdapter(
    val onClick: (news: NewsDomain, itemView: View) -> Unit
) :
    RecyclerView.Adapter<NewsAdapter.StandingsViewHolder>() {

    private var news: List<NewsDomain> = emptyList()

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

    fun updateList(newList: List<NewsDomain>, smoothUpdateListView: (DiffUtil.DiffResult) -> Unit){
        val result = DiffUtil.calculateDiff(NewsDiffCallback(news, newList))
        news = newList
        smoothUpdateListView(result)
    }

    inner class StandingsViewHolder(private val item: ItemNewsBinding) :
        RecyclerView.ViewHolder(item.root) {

        init {
            item.root.setOnClickListener { onClick(news[layoutPosition], it) }
        }

        fun bind(newsDomain: NewsDomain) {
            ViewCompat.setTransitionName(item.root, "news_${newsDomain.id}")
            item.ivItemNewsPicture.load(newsDomain.imageUrl) {
                crossfade(true)
            }
            item.tvItemNewsTitle.text = newsDomain.title
            item.tvItemNewsDate.text = convertDateToString(newsDomain.pubDate)
            item.tvItemNewsBody.text = getTextFromHtml(newsDomain.text)
        }
    }

    inner class NewsDiffCallback(
        private val oldList: List<NewsDomain>,
        private val newList: List<NewsDomain>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}