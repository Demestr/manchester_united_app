/*
 * Created by Dmitry Lipski on 13.01.21 10:45
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 13.01.21 9:11
 */

package com.lipssoftware.manchester.united.ui.standings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lipssoftware.manchester.united.data.model.domain.StandingDomain
import com.lipssoftware.manchester.united.databinding.ItemStandingsBinding

class StandingsAdapter(private val standings: List<StandingDomain>): RecyclerView.Adapter<StandingsAdapter.StandingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingsViewHolder =
        StandingsViewHolder(ItemStandingsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: StandingsViewHolder, position: Int) {
        holder.bind(standings[position])
    }

    override fun getItemCount(): Int = standings.size

    class StandingsViewHolder(private val item: ItemStandingsBinding): RecyclerView.ViewHolder(item.root){

        fun bind(standing: StandingDomain){
            item.tvItemStandingsRank.text = standing.rank.toString()
            item.ivItemStandingsLogo.load(standing.team.logo){
                crossfade(true)
            }
            item.tvItemStandingsTeam.text = standing.team.name
            item.tvItemStandingsGames.text = standing.all.played.toString()
            item.tvItemStandingsGoalDifference.text = standing.goalsDiff.toString()
            item.tvItemStandingsPoints.text = standing.points.toString()
        }
    }
}