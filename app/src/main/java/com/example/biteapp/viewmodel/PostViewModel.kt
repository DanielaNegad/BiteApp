package com.example.biteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.biteapp.data.PostEntity
import com.example.biteapp.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PostRepository(application)

    private val _posts = MutableStateFlow<List<PostEntity>>(emptyList())
    val posts: StateFlow<List<PostEntity>> = _posts

    fun loadPosts() {
        viewModelScope.launch {
            val firebasePosts = repository.fetchPostsFromFirebase()
            _posts.value = firebasePosts
        }
    }

    fun addPost(post: PostEntity) {
        viewModelScope.launch {
            repository.insertLocalPost(post)
            repository.uploadPostToFirebase(post)
            loadPosts() // רענון אחרי הוספה
        }
    }

    fun deletePost(post: PostEntity) {
        viewModelScope.launch {
            repository.deleteLocalPost(post)
            loadPosts() // רענון אחרי מחיקה
        }
    }
}
