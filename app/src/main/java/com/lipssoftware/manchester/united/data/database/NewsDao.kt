/*
 * Created by Dmitry Lipski on 12.02.21 9:10
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12.02.21 9:10
 */

package com.lipssoftware.manchester.united.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lipssoftware.manchester.united.data.model.domain.NewsDomain
import io.reactivex.Flowable

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(news: List<NewsDomain>)

    @Query("SELECT * FROM news ORDER BY pubDate DESC")
    fun getNews(): Flowable<List<NewsDomain>>

    @Query("SELECT EXISTS(SELECT * FROM news WHERE id = :newsId)")
    fun isExist(newsId: String): Boolean

    @Query("SELECT COUNT (*) FROM news")
    fun newsCount(): Int

    @Query("DELETE FROM news WHERE id = :id")
    fun deleteNews(id: String)
}