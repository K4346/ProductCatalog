package com.example.productcatalog.ui.adpaters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.productcatalog.R
import com.example.productcatalog.databinding.ProductItemBinding
import com.example.productcatalog.domain.entities.ProductInfoEntity

class ProductsListAdapter(
//    todo возможно засунуть слушатели в один класс
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
//            todo перенсти в функции
            ivPhoto.load(currentProduct.thumbnail) {
                crossfade(true)
//                placeholder(R.drawable.image)
                transformations(RoundedCornersTransformation())
            }
            tvTitle.text = currentProduct.title
            tvDescription.text = currentProduct.description
//            todo с валютой
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

    inner class ProductsListViewHolder(itemView: ProductItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val container = itemView.container
        val tvTitle = itemView.tvTitle
        val tvDescription = itemView.tvDescription
        val tvPrice = itemView.tvPrice
        val ivPhoto = itemView.ivPhoto

    }
}