package com.jesusvilla.test.database.entities.test

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "posts", indices = [Index(value = ["id"], unique = true)])
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val idEntity: Long = 0,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "body")
    val body: String
)
