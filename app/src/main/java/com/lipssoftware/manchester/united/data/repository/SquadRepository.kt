/*
 * Created by Dmitry Lipski on 14.01.21 15:00
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 14.01.21 14:50
 */

package com.lipssoftware.manchester.united.data.repository

import android.content.Context
import android.graphics.BitmapFactory
import androidx.core.graphics.scale
import com.google.gson.Gson
import com.lipssoftware.manchester.united.data.model.players.Player
import com.lipssoftware.manchester.united.data.model.players.Squad

class SquadRepository(val context: Context) : Repository {

    fun getSquad(): List<Player> {
        val gson = Gson()
        val jsonFile = context.assets.open("squad.json").bufferedReader().use { it.readText() }
        return gson.fromJson(jsonFile, Squad::class.java).players.map {
            Player(
                it.name,
                it.firstname,
                it.lastname,
                it.birth,
                it.nationality,
                it.position,
                it.height,
                it.weight,
                it.photo,
                BitmapFactory.decodeStream(context.assets.open("players_photos/${it.photo}")).scale(400, 680)
            )
        }
    }
}