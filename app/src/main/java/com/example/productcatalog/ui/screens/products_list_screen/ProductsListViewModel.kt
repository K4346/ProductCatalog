package com.example.productcatalog.ui.screens.products_list_screen

import android.app.Application
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.productcatalog.App
import com.example.productcatalog.SingleLiveEvent
import com.example.productcatalog.domain.entities.ProductInfoEntity
import com.example.productcatalog.domain.use_cases.ProductsListUseCases
import javax.inject.Inject

class ProductsListViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var productsListUseCases: ProductsListUseCases


    val productsMLE: MutableLiveData<List<ProductInfoEntity>>

    val categoriesMLE: MutableLiveData<List<String>>

    val showError: SingleLiveEvent<Unit>

    private var productsListPage = 1

    init {
        (application as App).component.inject(this)

        productsMLE = productsListUseCases.productsMLE

        categoriesMLE = productsListUseCases.categoriesMLE

        showError = productsListUseCases.showError

        productsListUseCases.updateProductsList(productsListPage)

        productsListUseCases.initCategories(application)
    }

    fun getNewProducts() {
        productsListPage++
        productsListUseCases.updateProductsList(productsListPage)
    }

    override fun onCleared() {
        super.onCleared()
        productsListUseCases.dispose()
    }

    fun getBundleForDetailScreen(position: Int): Bundle? {
        val product = productsMLE.value?.get(position) ?: return null
        return bundleOf(
            PRODUCT_BUNDLE_IMAGES_KEY to product.images,
            PRODUCT_BUNDLE_TITLE_KEY to product.title,
            PRODUCT_BUNDLE_DESCRIPTION_KEY to product.description,
            PRODUCT_BUNDLE_BRAND_KEY to product.brand,
            PRODUCT_BUNDLE_CATEGORY_KEY to product.category,
            PRODUCT_BUNDLE_RATING_KEY to product.rating,
            PRODUCT_BUNDLE_PRICE_KEY to product.price,
        )
    }

    companion object {
        const val PRODUCT_BUNDLE_IMAGES_KEY = "PRODUCT_BUNDLE_IMAGES_KEY"
        const val PRODUCT_BUNDLE_TITLE_KEY = "PRODUCT_BUNDLE_TITLE_KEY"
        const val PRODUCT_BUNDLE_DESCRIPTION_KEY = "PRODUCT_BUNDLE_DESCRIPTION_KEY"
        const val PRODUCT_BUNDLE_BRAND_KEY = "PRODUCT_BUNDLE_BRAND_KEY"
        const val PRODUCT_BUNDLE_CATEGORY_KEY = "PRODUCT_BUNDLE_CATEGORY_KEY"
        const val PRODUCT_BUNDLE_RATING_KEY = "PRODUCT_BUNDLE_RATING_KEY"
        const val PRODUCT_BUNDLE_PRICE_KEY = "PRODUCT_BUNDLE_PRICE_KEY"
    }
}