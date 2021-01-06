/*
 * Created by Dmitry Lipski on 06.01.21 9:27
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 06.01.21 9:27
 */

package com.lipssoftware.manchester.united.ui.standings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lipssoftware.manchester.united.data.model.standings.Standing
import com.lipssoftware.manchester.united.databinding.ItemStandingsBinding

class StandingsAdapter(private val standings: List<Standing>): RecyclerView.Adapter<StandingsAdapter.StandingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingsViewHolder =
        StandingsViewHolder(ItemStandingsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: StandingsViewHolder, position: Int) {
        holder.bind(standings[position])
    }

    override fun getItemCount(): Int = standings.size

    class StandingsViewHolder(private val item: ItemStandingsBinding): RecyclerView.ViewHolder(item.root){

        fun bind(standing: Standing){
            item.textStandingsRank.text = standing.rank.toString()
            item.textStandingsTeam.text = standing.team.name
            item.textStandingsGames.text = standing.all.played.toString()
            item.textStandingsGd.text = standing.goalsDiff.toString()
            item.textStandingsPoints.text = standing.points.toString()
        }
    }
}