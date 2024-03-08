package com.example.productcatalog.domain.repositories

import com.example.productcatalog.domain.entities.ProductsListEntity
import io.reactivex.Single

interface ProductsInfoListRepository {
    fun getProductsList(page: Int): Single<ProductsListEntity>

    fun getProductsListByCategory(page: Int, category: String): Single<ProductsListEntity>

    fun getProductsListBySearchedText(page: Int, searched: String): Single<ProductsListEntity>
    fun getCategories(): Single<List<String>>

}