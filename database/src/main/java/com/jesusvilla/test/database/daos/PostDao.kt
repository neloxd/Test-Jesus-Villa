package com.jesusvilla.test.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jesusvilla.test.database.entities.test.PostEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(posts: List<PostEntity>): List<Long>

    @Transaction
    @Query("SELECT * FROM POSTS ORDER BY id")
    suspend fun getPosts():  List<PostEntity>
}