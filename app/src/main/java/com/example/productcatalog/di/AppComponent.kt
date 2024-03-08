package com.example.productcatalog.di

import com.example.productcatalog.data.repositories.ProductsInfoListRepositoryImpl
import com.example.productcatalog.domain.use_cases.ProductsListUseCasesImpl
import com.example.productcatalog.ui.screens.products_list_screen.ProductsListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiFactoryModule::class, ApiServiceModule::class, RepositoriesModule::class, UseCasesModule::class])
interface AppComponent {

    fun inject(repository: ProductsInfoListRepositoryImpl)
    fun inject(viewModel: ProductsListViewModel)
    fun inject(useCases: ProductsListUseCasesImpl)
}