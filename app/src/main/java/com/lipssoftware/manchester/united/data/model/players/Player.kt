/*
 * Created by Dmitry Lipski on 15.01.21 17:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 14.01.21 15:44
 */

package com.lipssoftware.manchester.united.data.model.players

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    val name: String,
    val firstname: String,
    val lastname: String,
    val birth: Birth,
    val nationality: String,
    val position: String,
    val number: Int,
    val height: Int,
    val weight: Int,
    val photo: String) : Parcelable {
    @IgnoredOnParcel var thumbnail: Bitmap? = null
}
