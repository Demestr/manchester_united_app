/*
 * Created by Dmitry Lipski on 11.02.21 15:35
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.02.21 15:27
 */

package com.lipssoftware.manchester.united.data.model.players

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import androidx.core.graphics.scale
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.io.InputStream

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

    fun setCompressedThumbnail(inputStream: InputStream){
        thumbnail = BitmapFactory.decodeStream(inputStream).scale(400, 680)
    }
}
