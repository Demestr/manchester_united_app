/*
 * Created by Dmitry Lipski on 21.01.21 14:58
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 21.01.21 14:58
 */

package com.lipssoftware.manchester.united.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news: List<NewsDomain>)

    @Query("SELECT * FROM news ORDER BY pubDate DESC")
    fun getNews(): Flow<List<NewsDomain>>

    @Query("SELECT EXISTS(SELECT * FROM news WHERE id = :newsId)")
    fun isExist(newsId: String): Boolean

    @Query("SELECT COUNT (*) FROM news")
    fun newsCount(): Int

    @Query("DELETE FROM news WHERE id = :id")
    suspend fun deleteNews(id: String)
}