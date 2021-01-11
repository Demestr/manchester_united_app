/*
 * Created by Dmitry Lipski on 11.01.21 17:05
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 14:25
 */

package com.lipssoftware.manchester.united.data.model.news

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class RssChannel @JvmOverloads constructor(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String,
    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: String,
    @field:ElementList(name = "item", inline = true)
    @param:ElementList(name = "item", inline = true)
    val items: List<RssItem>
)
