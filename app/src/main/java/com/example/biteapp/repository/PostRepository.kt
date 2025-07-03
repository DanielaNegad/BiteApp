package com.example.biteapp.repository

import android.content.Context
import com.example.biteapp.data.DatabaseProvider
import com.example.biteapp.data.PostEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PostRepository(context: Context) {

    private val postDao = DatabaseProvider.getDatabase(context).postDao()
    private val firestore = FirebaseFirestore.getInstance()

    // 🔹 ROOM - פעולה מקומית
    suspend fun insertLocalPost(post: PostEntity) {
        postDao.insertPost(post)
    }

    suspend fun getLocalPosts(): List<PostEntity> {
        return postDao.getAllPosts()
    }

    suspend fun deleteLocalPost(post: PostEntity) {
        postDao.deletePost(post)
    }

    // 🔹 FIRESTORE - פעולה מרוחקת
    suspend fun uploadPostToFirebase(post: PostEntity) {
        firestore.collection("posts")
            .document(post.id)
            .set(post)
            .await()
    }

    suspend fun fetchPostsFromFirebase(): List<PostEntity> {
        val snapshot = firestore.collection("posts").get().await()
        return snapshot.documents.mapNotNull { it.toObject(PostEntity::class.java) }
    }
}
