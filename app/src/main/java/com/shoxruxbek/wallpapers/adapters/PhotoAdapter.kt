package com.shoxruxbek.wallpapers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shoxruxbek.wallpapers.databinding.PhotoItemBinding
import com.shoxruxbek.wallpapers.models.Hit
import com.squareup.picasso.Picasso
import com.squareup.picasso.Callback
class PhotoAdapter(var onItemClickListener: OnItemClickListener) : PagingDataAdapter<Hit, RecyclerView.ViewHolder>(MyDiffutil()) {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_PROGRESS = 1
    }

    inner class PhotoViewHolder(private val photoItemBinding: PhotoItemBinding) : RecyclerView.ViewHolder(photoItemBinding.root) {

        fun bind(hit: Hit?){
            photoItemBinding.apply {
                progressBar.visibility = View.VISIBLE // Show progress bar
                Picasso.get().load(hit!!.largeImageURL)
                    .into(photoItemBinding.rasm, object : Callback {
                        override fun onSuccess() {
                            progressBar.visibility = View.GONE // Hide progress bar when image loaded successfully
                        }

                        override fun onError(e: Exception?) {
                            // Handle error loading image
                            progressBar.visibility = View.GONE // Hide progress bar on error
                        }
                    })

                photoItemBinding.root.setOnClickListener {
                    onItemClickListener.onItemClick(hit)
                }

            }
        }
    }

    inner class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class MyDiffutil : DiffUtil.ItemCallback<Hit>() {
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoViewHolder -> holder.bind(getItem(position))
            is ProgressViewHolder -> {
                // Handle progress bar view holder
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PhotoViewHolder(binding)
            }
            VIEW_TYPE_PROGRESS -> {
                val progressBar = ProgressBar(parent.context)
                val layoutParams = RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                )
                progressBar.layoutParams = layoutParams
                ProgressViewHolder(progressBar)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount - 1) VIEW_TYPE_ITEM else VIEW_TYPE_PROGRESS
    }

    interface OnItemClickListener{
        fun onItemClick(hit: Hit?)
    }
}