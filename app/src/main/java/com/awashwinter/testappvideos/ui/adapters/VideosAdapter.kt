package com.awashwinter.testappvideos.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.awashwinter.testappvideos.databinding.ItemVideoBinding
import com.awashwinter.testappvideos.models.VideoItem
import com.bumptech.glide.Glide

class VideosAdapter : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    private val videoItems = arrayListOf<VideoItem>()
    private var onItemClickListener: ((VideoItem, Int) -> Unit)? = null

    val videos get() : List<VideoItem> = videoItems

    inner class VideoViewHolder(private val binding: ItemVideoBinding) : ViewHolder(binding.root) {

        fun bind(videoItem: VideoItem, position: Int) {
            binding.tvVideoName.text = videoItem.name
            binding.tvVideoUrl.text = videoItem.url
            Glide.with(binding.ivPreview).load(videoItem.imagePreviewUrl).into(binding.ivPreview)
            binding.cardItem.setOnClickListener {
                onItemClickListener?.invoke(videoItem, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding =
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun getItemCount(): Int = videoItems.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videoItems[position], position)
    }

    fun updateVideos(newVideos: List<VideoItem>) {
        val diffCallback = VideosDiffCallback(videoItems, newVideos)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        videoItems.clear()
        videoItems.addAll(newVideos)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(listener: (VideoItem, Int) -> Unit) {
        onItemClickListener = listener
    }
}