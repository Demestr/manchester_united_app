/*
 * Created by Dmitry Lipski on 20.01.21 16:30
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 20.01.21 14:56
 */

package com.lipssoftware.manchester.united.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news: List<NewsDomain>)

    @Query("SELECT * FROM news ORDER BY pubDate DESC")
    suspend fun getNews(): List<NewsDomain>

    @Query("SELECT EXISTS(SELECT * FROM news WHERE id = :newsId)")
    suspend fun isExist(newsId: String): Boolean
}