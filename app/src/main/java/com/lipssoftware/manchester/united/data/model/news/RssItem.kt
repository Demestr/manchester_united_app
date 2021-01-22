/*
 * Created by Dmitry Lipski on 22.01.21 12:30
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 22.01.21 11:43
 */

package com.lipssoftware.manchester.united.data.model.news

import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import com.lipssoftware.manchester.united.utils.convertStringToDate
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class RssItem @JvmOverloads constructor(
    @field:Attribute(name = "id")
    @param:Attribute(name = "id")
    val id: String,
    @field:Element(name = "title", required = false)
    @param:Element(name = "title", required = false)
    val title: String?,
    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: String,
    @field:Element(name = "category")
    @param:Element(name = "category")
    val category: String,
    @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    val pubDate: String,
    @field:Element(name = "newstext")
    @param:Element(name = "newstext")
    val newsText: String,
    @field:Element(name = "thumbnail")
    @param:Element(name = "thumbnail")
    val thumbnail: RssThumbnail
){

    fun toNewsDomain(): NewsDomain{
        return NewsDomain(
            id,
            title,
            category,
            link,
            convertStringToDate(pubDate),
            newsText,
            thumbnail.url
        )
    }
}
