/*
 * Created by Dmitry Lipski on 05.02.21 14:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 05.02.21 14:06
 */

package com.lipssoftware.manchester.united.ui.squad

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.lipssoftware.manchester.united.data.model.players.Player
import com.lipssoftware.manchester.united.databinding.ItemPlayerBinding


class SquadAdapter(private val players: List<Player>, val onClick: (player: Player, itemView: View) -> Unit) :
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

        init {
            item.root.setOnClickListener {
                onClick(players[layoutPosition], it)
            }
        }

        fun bind(player: Player) {
            ViewCompat.setTransitionName(item.root, "tn_${player.number}")
            item.tvItemPlayerName.text = player.name
            item.ivItemPlayerPhoto.setImageBitmap(player.thumbnail)
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
