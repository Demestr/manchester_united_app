/*
 * Created by Dmitry Lipski on 11.01.21 17:05
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 16:20
 */

package com.lipssoftware.manchester.united.data.model.news

data class NewsItem(
    val title: String?,
    val link: String,
    val pubDate: String,
    val text: String,
    val imageUrl: String
)
