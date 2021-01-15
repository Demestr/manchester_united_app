/*
 * Created by Dmitry Lipski on 15.01.21 17:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 14.01.21 15:33
 */

package com.lipssoftware.manchester.united.data.model.players

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Birth(
    val date: String,
    val place: String,
    val country: String
) : Parcelable
