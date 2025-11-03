package com.upii.post_pertemuan5

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmob.baseproj5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val posts = mutableListOf<Post>()
    private lateinit var postAdapter: PostAdapter
    private var tempDialog: AddPostDialog? = null

    private val storyList = listOf(
        "intan_dwi", "minda_04", "rubi_community",
        "rizka", "amel", "anugrah_saja", "pratama_tama"
    )

    // 🔹 Activity Result untuk ambil gambar
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                tempDialog?.updateSelectedImage(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 Story horizontal
        binding.rvStory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvStory.adapter = StoryAdapter(storyList)

        // 🔹 Post vertical
        postAdapter = PostAdapter(posts, onEdit = { editPost(it) }, onDelete = { deletePost(it) })
        binding.rvPost.layoutManager = LinearLayoutManager(this)
        binding.rvPost.adapter = postAdapter

        // 🔹 Tombol tambah
        binding.btnAdd.setOnClickListener {
            tempDialog = AddPostDialog(
                activity = this,
                onSave = { newPost ->
                    posts.add(newPost)
                    postAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "Post berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                },
                onPickImage = { pickImageLauncher.launch("image/*") }
            )
            tempDialog?.show()
        }
    }

    private fun editPost(post: Post) {
        tempDialog = AddPostDialog(
            activity = this,
            existingPost = post,
            onSave = { updated ->
                val index = posts.indexOfFirst { it.id == updated.id }
                if (index != -1) {
                    posts[index] = updated
                    postAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "Post berhasil diperbarui", Toast.LENGTH_SHORT).show()
                }
            },
            onPickImage = { pickImageLauncher.launch("image/*") }
        )
        tempDialog?.show()
    }

    private fun deletePost(post: Post) {
        posts.remove(post)
        postAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Post berhasil dihapus", Toast.LENGTH_SHORT).show()
    }
}