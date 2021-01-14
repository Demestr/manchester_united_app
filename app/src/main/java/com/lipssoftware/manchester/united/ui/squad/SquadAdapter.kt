/*
 * Created by Dmitry Lipski on 14.01.21 15:00
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 14.01.21 14:40
 */

package com.lipssoftware.manchester.united.ui.squad

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.lipssoftware.manchester.united.data.model.players.Player
import com.lipssoftware.manchester.united.databinding.ItemPlayerBinding


class SquadAdapter(private val players: List<Player>) :
    RecyclerView.Adapter<SquadAdapter.SquadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquadViewHolder =
        SquadViewHolder(
            ItemPlayerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SquadViewHolder, position: Int) {
        holder.bind(players[position])
    }

    override fun getItemCount(): Int = players.size

    inner class SquadViewHolder(private val item: ItemPlayerBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bind(player: Player) {
            item.tvItemPlayerName.text = player.name
            item.ivItemPlayerPhoto.setImageDrawable(player.thumbnail.toDrawable(item.root.resources))
        }
    }

    //Just copy-paste from stackoverflow
    class PlayerItemDecoration(
        private val space: Int,
        private val spanCount: Int,
        private val includeEdge: Boolean
    ) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)

            val column: Int = position % spanCount

            if (includeEdge) {
                outRect.left =
                    space - column * space / spanCount
                outRect.right =
                    (column + 1) * space / spanCount
                if (position < spanCount) {
                    outRect.top = space
                }
                outRect.bottom = space
            } else {
                outRect.left = column * space / spanCount
                outRect.right =
                    space - (column + 1) * space / spanCount
                if (position >= spanCount) {
                    outRect.top = space
                }
            }
        }
    }
}
