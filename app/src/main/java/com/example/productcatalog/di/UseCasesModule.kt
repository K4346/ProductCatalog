package com.example.productcatalog.di

import com.example.productcatalog.domain.use_cases.ProductsListUseCases
import com.example.productcatalog.domain.use_cases.ProductsListUseCasesImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {
    @Provides
    @Singleton
    fun provideProductsListUseCases(): ProductsListUseCases {
        return ProductsListUseCasesImpl()
    }

}