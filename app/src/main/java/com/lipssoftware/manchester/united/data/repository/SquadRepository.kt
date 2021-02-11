/*
 * Created by Dmitry Lipski on 11.02.21 14:44
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 14:43
 */

package com.lipssoftware.manchester.united.data.repository

import android.content.Context
import android.graphics.BitmapFactory
import androidx.core.graphics.scale
import com.google.gson.Gson
import com.lipssoftware.manchester.united.data.model.players.Player
import com.lipssoftware.manchester.united.data.model.players.Squad
import io.reactivex.Single
import javax.inject.Inject

class SquadRepository @Inject constructor(val context: Context) : Repository {

    fun getSquad(): Single<List<Player>> {
        val gson = Gson()
        val jsonFile = context.assets.open("squad.json").bufferedReader().use { it.readText() }
        return Single.just(gson.fromJson(jsonFile, Squad::class.java).players.map { player ->
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
        })
    }
}