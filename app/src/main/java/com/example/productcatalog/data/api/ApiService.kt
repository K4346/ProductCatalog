package com.example.productcatalog.data.api

import com.example.productcatalog.domain.entities.ProductsListEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(value = "products")
    fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Single<ProductsListEntity>
    
}