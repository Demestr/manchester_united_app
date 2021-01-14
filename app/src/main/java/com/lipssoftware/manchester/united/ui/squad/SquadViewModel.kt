/*
 * Created by Dmitry Lipski on 14.01.21 15:00
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 14.01.21 14:56
 */

package com.lipssoftware.manchester.united.ui.squad

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lipssoftware.manchester.united.data.model.players.Player
import com.lipssoftware.manchester.united.data.repository.SquadRepository
import com.lipssoftware.manchester.united.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SquadViewModel(val context: Context) : ViewModel() {

    private val _players = MutableLiveData<Resource<List<Player>>>()
    val players: LiveData<Resource<List<Player>>>
        get() = _players

    fun getSquad() {
        viewModelScope.launch(Dispatchers.Default) {
            _players.postValue(Resource.loading(data = null))
            try{
                _players.postValue(Resource.success(data = SquadRepository(context).getSquad()))
            }
            catch (exception: Exception){
                _players.postValue(Resource.error(data = null, message = exception.message ?: "Error occurred"))
            }
        }
    }
}