package com.pmob.baseproj5

import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.sosmed.model.Post
import com.pmob.baseproj5.databinding.DialogAddPostBinding

class AddPostDialog(
    private val activity: Activity,
    private val existingPost: Post? = null,
    private val onSave: (Post) -> Unit,
    private val onPickImage: () -> Unit
) {
    private var imageUri: Uri? = null
    private lateinit var binding: DialogAddPostBinding
    private lateinit var dialog: AlertDialog

    fun show() {
        binding = DialogAddPostBinding.inflate(LayoutInflater.from(activity))
        dialog = AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()

        existingPost?.let {
            binding.etUsername.setText(it.username)
            binding.etCaption.setText(it.caption)
            imageUri = Uri.parse(it.imageUri)
            binding.ivPreview.setImageURI(imageUri)
            binding.ivPreview.visibility = View.VISIBLE
        }

        binding.btnPickImage.setOnClickListener {
            onPickImage()
        }

        binding.btnSave.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val caption = binding.etCaption.text.toString().trim()

            if (username.isEmpty() || caption.isEmpty() || imageUri == null) {
                Toast.makeText(activity, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show()
            } else {
                val post = if (existingPost != null) {
                    // 🔹 Kalau sedang edit, pertahankan ID lama
                    existingPost.copy(
                        username = username,
                        caption = caption,
                        imageUri = imageUri.toString()
                    )
                } else {
                    // 🔹 Kalau baru, buat post baru
                    Post(
                        id = (0..999999).random(),
                        username = username,
                        caption = caption,
                        imageUri = imageUri.toString()
                    )
                }

                onSave(post)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    fun updateSelectedImage(uri: Uri) {
        imageUri = uri
        binding.ivPreview.setImageURI(uri)
        binding.ivPreview.visibility = View.VISIBLE
    }
}