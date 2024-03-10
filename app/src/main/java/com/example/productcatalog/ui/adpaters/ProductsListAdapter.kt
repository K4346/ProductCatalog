package com.example.productcatalog.ui.adpaters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.productcatalog.R
import com.example.productcatalog.databinding.ProductItemBinding
import com.example.productcatalog.domain.entities.ProductInfoEntity

class ProductsListAdapter(
    private val appContext: Context,
    private val productsOverListener: () -> Unit,
    private val onProductClickListener: (position: Int) -> Unit
) : RecyclerView.Adapter<ProductsListAdapter.ProductsListViewHolder>() {

    var list: List<ProductInfoEntity> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsListViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsListViewHolder, position: Int) {
        val currentProduct = list[position]
        with(holder) {

            initProductPicture(ivPhoto, currentProduct.thumbnail)

            tvTitle.text = currentProduct.title
            tvDescription.text = currentProduct.description
            tvPrice.text =
                appContext.getString(R.string.product_price, currentProduct.price.toString())


            container.setOnClickListener {
                onProductClickListener.invoke(position)
            }
        }

        if (position == itemCount - 2) {
            productsOverListener.invoke()
        }
    }

    override fun getItemCount(): Int = list.size

    private fun initProductPicture(ivPhoto: ImageView, thumbnail: String) {
        ivPhoto.load(thumbnail) {
            crossfade(true)
            transformations(RoundedCornersTransformation())
        }
    }

    inner class ProductsListViewHolder(itemView: ProductItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val container = itemView.container
        val tvTitle = itemView.tvTitle
        val tvDescription = itemView.tvDescription
        val tvPrice = itemView.tvPrice
        val ivPhoto = itemView.ivPhoto

    }
}