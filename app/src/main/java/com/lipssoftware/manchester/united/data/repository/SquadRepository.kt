/*
 * Created by Dmitry Lipski on 11.02.21 15:35
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 15:35
 */

package com.lipssoftware.manchester.united.data.repository

import android.content.Context
import com.google.gson.Gson
import com.lipssoftware.manchester.united.data.model.players.Player
import com.lipssoftware.manchester.united.data.model.players.Squad
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SquadRepository @Inject constructor(val context: Context) : Repository {

    fun getSquad(): Single<List<Player>> {
       return openPlayersFile()
           .subscribeOn(Schedulers.io())
           .flatMap { file -> getPlayersWithCompressedProfileImages(file) }
    }

    private fun getPlayersWithCompressedProfileImages(jsonFile: String): Single<List<Player>> {
        return Single.just(Gson().fromJson(jsonFile, Squad::class.java).players.map { player ->
            player.also{ it.setCompressedThumbnail(context.assets.open("players_photos/${player.photo}")) }
        })
    }

    private fun openPlayersFile(): Single<String> =
        Single.just(context.assets.open("squad.json").bufferedReader().use { it.readText() })

}