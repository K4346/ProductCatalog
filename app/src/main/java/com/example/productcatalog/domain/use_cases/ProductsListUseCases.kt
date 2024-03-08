package com.example.productcatalog.domain.use_cases

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.productcatalog.App
import com.example.productcatalog.R
import com.example.productcatalog.SingleLiveEvent
import com.example.productcatalog.domain.entities.ProductInfoEntity
import com.example.productcatalog.domain.entities.ProductsAboutInfoEntity
import com.example.productcatalog.domain.entities.ProductsShowType.Category
import com.example.productcatalog.domain.entities.ProductsShowType.Normal
import com.example.productcatalog.domain.entities.ProductsShowType.Search
import com.example.productcatalog.domain.repositories.ProductsInfoListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface ProductsListUseCases {
    fun updateProductsList(
        page: Int,
        clearOldProductsFlag: Boolean,
        productsAboutInfo: ProductsAboutInfoEntity
    )

    fun initCategories(context: Context)

    fun dispose()

    val productsMLE: MutableLiveData<List<ProductInfoEntity>>

    val categoriesMLE: MutableLiveData<List<String>>

    val showError: SingleLiveEvent<Unit>
}

class ProductsListUseCasesImpl : ProductsListUseCases {

    @Inject
    lateinit var repository: ProductsInfoListRepository

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override val productsMLE = MutableLiveData<List<ProductInfoEntity>>()

    override val categoriesMLE = MutableLiveData<List<String>>()

    override val showError = SingleLiveEvent<Unit>()

    init {
        App().component.inject(this)
    }

    override fun updateProductsList(
        page: Int,
        clearOldProductsFlag: Boolean,
        productsAboutInfo: ProductsAboutInfoEntity
    ) {
        val observable = when (productsAboutInfo.productsShowType) {
            Normal -> repository.getProductsList(page)
            Search -> repository.getProductsListBySearchedText(
                page,
                productsAboutInfo.dataForRequest ?: ""
            )

            Category -> repository.getProductsListByCategory(
                page,
                category = productsAboutInfo.dataForRequest ?: ""
            )
        }

        val disposable = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                if (clearOldProductsFlag) {
                    productsMLE.value = it.products
                } else {
                    val list = arrayListOf<ProductInfoEntity>()
                    productsMLE.value?.let { it1 -> list.addAll(it1) }
                    list.addAll(it.products)
                    productsMLE.value = list
                }
            }, {
                showError.call()
            })
        compositeDisposable.add(disposable)
    }

    override fun initCategories(context: Context) {
        val disposable = repository.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                categoriesMLE.value = listOf(context.getString(R.string.filter)) + it
            }, {
//                todo обработать
            })
        compositeDisposable.add(disposable)
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }
}