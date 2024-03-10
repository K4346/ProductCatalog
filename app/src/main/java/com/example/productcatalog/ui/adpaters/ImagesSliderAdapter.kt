package com.example.productcatalog.ui.adpaters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.productcatalog.R
import com.example.productcatalog.databinding.ImageSliderItemBinding

class ImagesSliderAdapter(private val imageUrls: List<String>) :
    RecyclerView.Adapter<ImagesSliderAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            ImageSliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        initImageView(holder.ivPhoto, position)

        initSwipeIcon(holder.ivSwipe, position)
    }

    private fun initImageView(ivPhoto: ImageView, position: Int) {
        ivPhoto.load(imageUrls[position]) {
            crossfade(true)
            transformations(RoundedCornersTransformation())
        }
    }

    private fun initSwipeIcon(ivSwipe: ImageView, position: Int) {
        if (itemCount == 1) {
            ivSwipe.isVisible = false
        } else {
            ivSwipe.isVisible = true

            ivSwipe.setImageResource(
                when (position) {
                    0 -> R.drawable.ic_swipe_right
                    itemCount - 1 -> R.drawable.ic_swipe_left
                    else -> R.drawable.ic_swipe
                }
            )
        }
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    inner class ImageViewHolder(itemView: ImageSliderItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val ivPhoto: ImageView = itemView.ivPhoto
        val ivSwipe: ImageView = itemView.ivSwipe
    }
}