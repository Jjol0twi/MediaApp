package com.example.mediaapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mediaapp.data.model.channel.ChItem
import com.example.mediaapp.databinding.HomeRcvItemChannelBinding
import com.example.mediaapp.ui.adapter.DifferCallback

class HomeChannelRcvAdapter() :
    ListAdapter<ChItem, HomeChannelRcvHolder>(DifferCallback.channelDifferCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeChannelRcvHolder {
        return HomeChannelRcvHolder(
            HomeRcvItemChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeChannelRcvHolder, position: Int) {
        val item = currentList[position]
        holder.itemView.setOnClickListener {
        }
        val category = currentList[position]
        val snippet = category.snippet
        val thumbnails = snippet.thumbnails

        holder.apply {
            channelThumbnails.load(thumbnails.medium.url)
            channelTitle.text = snippet.title
        }
    }

}

class HomeChannelRcvHolder(binding: HomeRcvItemChannelBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val channelThumbnails: ImageView = binding.homeRcvImgChannelThumbnail
    val channelTitle: TextView = binding.homeRcvTvChannelTitle

}