/*
 * Created by Dmitry Lipski on 11.01.21 12:53
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 12:53
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
            item.textStandingsRank.text = standing.rank.toString()
            item.imageTeam.load(standing.team.logo){
                crossfade(true)
            }
            item.textStandingsTeam.text = standing.team.name
            item.textStandingsGames.text = standing.all.played.toString()
            item.textStandingsGd.text = standing.goalsDiff.toString()
            item.textStandingsPoints.text = standing.points.toString()
        }
    }
}