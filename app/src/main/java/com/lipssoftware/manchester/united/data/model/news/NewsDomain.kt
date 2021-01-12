/*
 * Created by Dmitry Lipski on 12.01.21 16:56
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12.01.21 10:17
 */

package com.lipssoftware.manchester.united.data.model.news

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
    val pubDate: String,
    val text: String,
    val imageUrl: String
) : Parcelable
