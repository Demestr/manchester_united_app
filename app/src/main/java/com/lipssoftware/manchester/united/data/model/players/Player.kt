/*
 * Created by Dmitry Lipski on 14.01.21 15:00
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 14.01.21 14:40
 */

package com.lipssoftware.manchester.united.data.model.players

import android.graphics.Bitmap

data class Player(
    val name: String,
    val firstname: String,
    val lastname: String,
    val birth: Birth,
    val nationality: String,
    val position: String,
    val height: Int,
    val weight: Int,
    val photo: String,
    val thumbnail: Bitmap
)
