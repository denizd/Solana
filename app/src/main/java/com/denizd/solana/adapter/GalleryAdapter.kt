package com.denizd.solana.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.denizd.solana.data.GalleryItem
import com.denizd.solana.databinding.ItemGalleryBinding

class GalleryAdapter(
    private var items: List<GalleryItem> = listOf(),
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemGalleryBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {

        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<GalleryItem>) {
        this.items = items
    }
}