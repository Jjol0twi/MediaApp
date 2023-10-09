package com.example.mediaapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.mediaapp.databinding.SearchChannelItemBinding
import com.example.mediaapp.ui.search.model.SearchChannelEntity

class SearchChannelListAdapter() :
    RecyclerView.Adapter<SearchChannelListAdapter.ChannelViewHolder>() {

    private val dataList: ArrayList<SearchChannelEntity> = arrayListOf()

    fun refreshList() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun addDataList(items: ArrayList<SearchChannelEntity>) {
        dataList.addAll(items)
        notifyDataSetChanged()
    }

    class ChannelViewHolder(private val binding: SearchChannelItemBinding) :
        ViewHolder(binding.root) {
        fun bind(item: SearchChannelEntity) = with(binding) {
            itemImgChannel.load(item.channelImage)
            itemTxtName.text = item.channel
            itemTxtDescription.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        return ChannelViewHolder(
            SearchChannelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }
}