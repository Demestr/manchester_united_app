/*
 * Created by Dmitry Lipski on 11.01.21 17:05
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 14:07
 */

package com.lipssoftware.manchester.united.data.model.news

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssFeed @JvmOverloads constructor(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    val channel: RssChannel
)