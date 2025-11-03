package com.upii.post_pertemuan5

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmob.baseproj5.R
import com.pmob.baseproj5.databinding.ItemPostBinding

class PostAdapter(
    private val posts: MutableList<Post>,
    private val onEdit: (Post) -> Unit,
    private val onDelete: (Post) -> Unit
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]

        // Tampilkan username, caption, dan gambar
        holder.binding.tvUsername.text = post.username
        holder.binding.tvCaption.text = post.caption
        Glide.with(holder.itemView.context)
            .load(post.imageUri)
            .into(holder.binding.imgPost)

        // Tombol titik tiga (menu popup)
        holder.binding.btnMore.setOnClickListener { view ->
            val popup = PopupMenu(holder.itemView.context, view)
            popup.menuInflater.inflate(R.menu.menu_post, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        onEdit(post)
                        true
                    }
                    R.id.action_delete -> {
                        onDelete(post)
                        true
                    }
                    else -> false
                }
            }

            popup.show()
        }
    }
}
