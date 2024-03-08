package com.example.productcatalog.domain.repositories

import com.example.productcatalog.domain.entities.ProductsListEntity
import io.reactivex.Single

interface ProductsInfoListRepository {
    fun getProductsList(page: Int): Single<ProductsListEntity>
}