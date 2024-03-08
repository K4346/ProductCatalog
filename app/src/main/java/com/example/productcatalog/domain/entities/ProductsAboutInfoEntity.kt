package com.example.productcatalog.domain.entities


//todo переименовать
data class ProductsAboutInfoEntity(
    val productsShowType: ProductsShowType,
    val dataForRequest: String?
)

enum class ProductsShowType {
    Normal, Search, Category
}