package com.example.productcatalog.di

import com.example.productcatalog.data.repositories.ProductsInfoListRepositoryImpl
import com.example.productcatalog.domain.repositories.ProductsInfoListRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {
    @Provides
    @Singleton
    fun provideExchangeRateInfoRepository(): ProductsInfoListRepository {
        return ProductsInfoListRepositoryImpl()
    }

}