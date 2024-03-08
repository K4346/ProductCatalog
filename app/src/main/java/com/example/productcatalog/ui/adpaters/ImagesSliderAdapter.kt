package com.example.productcatalog.ui.adpaters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.productcatalog.databinding.ImageSliderItemBinding

class ImagesSliderAdapter(private val imageUrls: List<String>) :
    RecyclerView.Adapter<ImagesSliderAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            ImageSliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.ivPhoto.load(imageUrls[position]) {
            crossfade(true)
//                placeholder(R.drawable.image)
                 //transformations(CircleCropTransformation())
        }
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    inner class ImageViewHolder(itemView: ImageSliderItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val ivPhoto: ImageView = itemView.ivPhoto
    }
}