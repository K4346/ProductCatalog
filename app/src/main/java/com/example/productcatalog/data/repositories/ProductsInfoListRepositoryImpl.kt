package com.example.productcatalog.data.repositories

import com.example.productcatalog.App
import com.example.productcatalog.data.api.ApiService
import com.example.productcatalog.domain.entities.ProductsListEntity
import com.example.productcatalog.domain.repositories.ProductsInfoListRepository
import io.reactivex.Single
import javax.inject.Inject

class ProductsInfoListRepositoryImpl : ProductsInfoListRepository {
    @Inject
    lateinit var apiService: ApiService

    init {
        App().component.inject(this)
    }

    override fun getProductsList(page: Int): Single<ProductsListEntity> {
//        NOTE: Начинаем с единицы
        val skip = (page - 1) * LIMIT_PRODUCTS_QUERY
        return apiService.getProducts(skip = skip, LIMIT_PRODUCTS_QUERY)
    }

    override fun getCategories(): Single<List<String>> {
        return apiService.getCategories()

    }

    companion object {
        private const val LIMIT_PRODUCTS_QUERY = 20
    }
}