/*
 * Created by Dmitry Lipski on 05.02.21 14:06
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 05.02.21 9:45
 */

package com.lipssoftware.manchester.united.ui.squad.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lipssoftware.manchester.united.data.model.players.Player

class PlayerProfileViewModel : ViewModel() {

    private val _player = MutableLiveData<Player>()
    val player: LiveData<Player>
    get() = _player

    fun setPlayer(player: Player){
        _player.value = player
    }
}