/*
 * Created by Dmitry Lipski on 25.01.21 13:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 25.01.21 10:53
 */

package com.lipssoftware.manchester.united.data.repository

import android.content.Context
import android.graphics.BitmapFactory
import androidx.core.graphics.scale
import com.google.gson.Gson
import com.lipssoftware.manchester.united.data.model.players.Player
import com.lipssoftware.manchester.united.data.model.players.Squad
import javax.inject.Inject

class SquadRepository @Inject constructor(val context: Context) : Repository {

    fun getSquad(): List<Player> {
        val gson = Gson()
        val jsonFile = context.assets.open("squad.json").bufferedReader().use { it.readText() }
        return gson.fromJson(jsonFile, Squad::class.java).players.map { player ->
            Player(
                player.name,
                player.firstname,
                player.lastname,
                player.birth,
                player.nationality,
                player.position,
                player.number,
                player.height,
                player.weight,
                player.photo
            ).also {
                it.thumbnail = BitmapFactory.decodeStream(context.assets.open("players_photos/${it.photo}"))
                    .scale(400, 680)
            }
        }
    }
}