package com.example.biteapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)

    @Delete
    suspend fun deletePost(post: PostEntity)

    @Query("SELECT * FROM posts ORDER BY timestamp DESC")
    suspend fun getAllPosts(): List<PostEntity>

    @Query("SELECT * FROM posts WHERE userId = :userId ORDER BY timestamp DESC")
    suspend fun getPostsByUser(userId: String): List<PostEntity>
}
