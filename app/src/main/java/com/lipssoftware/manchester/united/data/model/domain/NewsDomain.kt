/*
 * Created by Dmitry Lipski on 21.01.21 14:58
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 20.01.21 16:31
 */

package com.lipssoftware.manchester.united.data.model.domain

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "news")
data class NewsDomain(
    @PrimaryKey val id: String,
    val title: String?,
    val link: String,
    val pubDate: Long,
    val text: String,
    val imageUrl: String
) : Parcelable
