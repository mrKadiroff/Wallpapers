package com.example.wallpapers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpapers.databinding.PhotoItemBinding
import com.example.wallpapers.models.Hit
import com.squareup.picasso.Picasso
import com.squareup.picasso.Callback
class PhotoAdapter2(private val lifecycleOwner: LifecycleOwner, private val onItemClickListener: OnItemClickListener) : PagingDataAdapter<Hit, RecyclerView.ViewHolder>(MyDiffutil()) {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_PROGRESS = 1
    }

    inner class PhotoViewHolder(private val photoItemBinding: PhotoItemBinding) : RecyclerView.ViewHolder(photoItemBinding.root) {

        fun bind(hit: Hit?) {
            photoItemBinding.apply {
                if (hit != null) {
                    progressBar.visibility = View.VISIBLE // Show progress bar
                    Picasso.get().load(hit.largeImageURL)
                        .into(rasm, object : Callback {
                            override fun onSuccess() {
                                progressBar.visibility = View.GONE // Hide progress bar when image loaded successfully
                            }

                            override fun onError(e: Exception?) {
                                // Handle error loading image
                                progressBar.visibility = View.GONE // Hide progress bar on error
                            }
                        })

                    root.setOnClickListener {
                        onItemClickListener.onItemClick(hit)
                    }
                } else {
                    // Clear any previous image or progress bar state
                    rasm.setImageDrawable(null)
                    progressBar.visibility = View.GONE
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

    // Function to clear the list and submit new data
    fun updateList(newHits: PagingData<Hit>) {
        submitData(lifecycleOwner.lifecycle, newHits)
    }

    // Function to clear the adapter list
    fun clearList() {
        submitData(lifecycleOwner.lifecycle, PagingData.empty())
    }

    interface OnItemClickListener {
        fun onItemClick(hit: Hit?)
    }
}
