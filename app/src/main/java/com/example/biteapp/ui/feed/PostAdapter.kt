package com.example.biteapp.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.biteapp.data.PostEntity
import com.example.biteapp.databinding.ItemPostBinding
import com.squareup.picasso.Picasso

class PostAdapter : ListAdapter<PostEntity, PostAdapter.PostViewHolder>(DiffCallback()) {

    class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostEntity) {
            binding.textContent.text = post.content
            binding.textUserId.text = post.userId
            Picasso.get().load(post.imageUrl).into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<PostEntity>() {
        override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity) = oldItem == newItem
    }
}
