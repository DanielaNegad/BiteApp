package com.example.biteapp.ui.createpost

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.biteapp.data.PostEntity
import com.example.biteapp.databinding.FragmentCreatePostBinding
import com.example.biteapp.viewmodel.PostViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class CreatePostFragment : Fragment() {

    private var _binding: FragmentCreatePostBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostViewModel by viewModels()
    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance().reference

    private var imageUri: Uri? = null
    private val IMAGE_REQUEST_CODE = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.buttonChooseImage.setOnClickListener {
            pickImageFromGallery()
        }

        binding.buttonUploadPost.setOnClickListener {
            val content = binding.editTextContent.text.toString().trim()
            if (content.isEmpty()) {
                Toast.makeText(requireContext(), "Please write something", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = auth.currentUser?.uid ?: "anonymous"
            val postId = UUID.randomUUID().toString()

            if (imageUri != null) {
                val imageRef = storage.child("post_images/$postId.jpg")
                imageRef.putFile(imageUri!!)
                    .addOnSuccessListener {
                        imageRef.downloadUrl.addOnSuccessListener { uri ->
                            uploadPost(content, uri.toString(), userId, postId)
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show()
                    }
            } else {
                uploadPost(content, "", userId, postId)
            }
        }
    }

    private fun uploadPost(content: String, imageUrl: String, userId: String, postId: String) {
        val post = PostEntity(
            id = postId,
            userId = userId,
            content = content,
            imageUrl = imageUrl,
            timestamp = System.currentTimeMillis()
        )

        viewModel.addPost(post)
        Toast.makeText(requireContext(), "Post uploaded", Toast.LENGTH_SHORT).show()
        binding.editTextContent.setText("")
        binding.imageViewPreview.setImageURI(null)
        binding.imageViewPreview.visibility = View.GONE
        imageUri = null
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            binding.imageViewPreview.setImageURI(imageUri)
            binding.imageViewPreview.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
