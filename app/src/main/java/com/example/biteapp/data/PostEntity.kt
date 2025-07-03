package com.example.biteapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String, // יכול להיות UUID או מזהה מ-Firebase
    val userId: String,
    val content: String,
    val imageUrl: String,
    val timestamp: Long
)
