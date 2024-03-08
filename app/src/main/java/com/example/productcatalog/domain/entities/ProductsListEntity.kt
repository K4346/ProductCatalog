package com.example.productcatalog.domain.entities

data class ProductsListEntity(
    val limit: Int,
    val products: List<ProductInfoEntity>,
    val skip: Int,
    val total: Int
)

data class ProductInfoEntity(
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
)