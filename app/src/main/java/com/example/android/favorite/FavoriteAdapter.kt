package com.example.android.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.component.OnItemClickListener
import com.example.android.component.UserSettingsModel
import com.example.android.core.loadImage
import com.example.android.databinding.FavoriteListItemBinding

class FavoriteAdapter : ListAdapter<String, FavoriteAdapter.ViewHolder>(diffCallback) {

    lateinit var onItemClickListener: OnItemClickListener

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            else -> ViewHolder.ItemViewHolder(FavoriteListItemBinding.inflate(inflater, parent, false), onItemClickListener)
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
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
            private val binding: FavoriteListItemBinding,
            onItemClickListener: OnItemClickListener
        ) : ViewHolder(
            binding.root
        ) {

            init {
                binding.ivFavorite.setOnClickListener {
                    onItemClickListener.onItemClick(it, layoutPosition)
                }
            }

            fun bind(item: String?) {
                val favorites = UserSettingsModel.favorites.value

                val imageUrl = item

                binding.ivImage.loadImage(imageUrl)
                binding.ivFavorite.tag = imageUrl
                binding.ivFavorite.setImageResource(if (favorites.contains(imageUrl)) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
            }
        }
    }
}