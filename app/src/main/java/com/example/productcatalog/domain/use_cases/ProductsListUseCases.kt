package com.example.productcatalog.domain.use_cases

import androidx.lifecycle.MutableLiveData
import com.example.productcatalog.App
import com.example.productcatalog.domain.entities.ProductInfoEntity
import com.example.productcatalog.domain.repositories.ProductsInfoListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface ProductsListUseCases {
    fun updateProductsList(page: Int)

    fun dispose()

    val productsMLE: MutableLiveData<List<ProductInfoEntity>>
}

class ProductsListUseCasesImpl : ProductsListUseCases {

    @Inject
    lateinit var repository: ProductsInfoListRepository

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override val productsMLE = MutableLiveData<List<ProductInfoEntity>>()

    init {
        App().component.inject(this)
    }

    override fun updateProductsList(page: Int) {
        val disposable = repository.getProductsList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                val list = arrayListOf<ProductInfoEntity>()
                productsMLE.value?.let { it1 -> list.addAll(it1) }
                list.addAll(it.products)
                productsMLE.value = list
            }, {
//                todo обработать
            })
        compositeDisposable.add(disposable)
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }
}