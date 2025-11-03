package com.upii.post_pertemuan5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pmob.baseproj5.databinding.ItemStoryBinding

class StoryAdapter(private val list: List<String>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.storyName.text = list[position]
    }
}