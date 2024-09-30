package com.awashwinter.testappvideos.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.awashwinter.testappvideos.models.VideoItem

class VideosDiffCallback (
    private val oldItems: List<VideoItem>,
    private val newItems: List<VideoItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size
    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldItems[oldItemPosition].name.equals(newItems[newItemPosition].name)
                && oldItems[oldItemPosition].url.equals(newItems[newItemPosition].url)
                && oldItems[oldItemPosition].imagePreviewUrl.equals(newItems[newItemPosition].imagePreviewUrl))
    }
}