package com.example.biteapp.model

data class Post(
    val id: String = "", // UID בפיירבייס או מפתח ב־Room
    val userId: String = "", // מזהה יוצר הפוסט
    val content: String = "", // טקסט חופשי
    val imageUrl: String = "", // קישור לתמונה ב־Firebase Storage
    val timestamp: Long = System.currentTimeMillis() // תאריך יצירה
)
