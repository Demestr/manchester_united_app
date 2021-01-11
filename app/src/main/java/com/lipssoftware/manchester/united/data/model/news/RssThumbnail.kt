/*
 * Created by Dmitry Lipski on 11.01.21 17:05
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.01.21 16:10
 */

package com.lipssoftware.manchester.united.data.model.news

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "media:thumbnail", strict = false)
data class RssThumbnail @JvmOverloads constructor(
    @field:Attribute(name = "url")
    @param:Attribute(name = "url")
    val url: String
)
