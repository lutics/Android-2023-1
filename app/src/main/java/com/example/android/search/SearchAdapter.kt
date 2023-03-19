package com.example.android.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.component.OnItemClickListener
import com.example.android.component.UserSettingsModel
import com.example.android.core.loadImage
import com.example.android.databinding.SearchListItemBinding
import com.example.android.item.Item
import java.text.SimpleDateFormat

class SearchAdapter : PagingDataAdapter<Item, SearchAdapter.ViewHolder>(diffCallback) {

    lateinit var onItemClickListener: OnItemClickListener

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            else -> ViewHolder.ItemViewHolder(SearchListItemBinding.inflate(inflater, parent, false), onItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.ItemViewHolder -> holder.bind(getItem(position))
        }
    }

    sealed class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(
        itemView
    ) {

        class ItemViewHolder(
            private val binding: SearchListItemBinding,
            onItemClickListener: OnItemClickListener
        ) : ViewHolder(
            binding.root
        ) {

            init {
                binding.ivFavorite.setOnClickListener {
                    onItemClickListener.onItemClick(it, layoutPosition)
                }
            }

            fun bind(item: Item?) {
                val favorites = UserSettingsModel.favorites.value

                val imageUrl = item?.imageUrl
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                binding.ivImage.loadImage(imageUrl)
                binding.tvName.text = item?.datetime?.let { simpleDateFormat.format(it) }
                binding.ivFavorite.tag = imageUrl
                binding.ivFavorite.setImageResource(if (favorites.contains(imageUrl)) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
            }
        }
    }
}