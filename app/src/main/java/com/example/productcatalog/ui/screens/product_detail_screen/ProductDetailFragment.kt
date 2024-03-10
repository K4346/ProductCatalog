package com.example.productcatalog.ui.screens.product_detail_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.productcatalog.R
import com.example.productcatalog.databinding.FragmentProductDetailBinding
import com.example.productcatalog.ui.adpaters.ImagesSliderAdapter
import com.example.productcatalog.ui.screens.products_list_screen.ProductsListViewModel

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductDetailViewModel by viewModels()

    private lateinit var adapter: ImagesSliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()


    }

    private fun initViews() {
        val bundle: Bundle = arguments ?: return

        with(binding) {
            tvTitle.text = bundle.getString(ProductsListViewModel.PRODUCT_BUNDLE_TITLE_KEY)
            tvDescription.text =
                bundle.getString(ProductsListViewModel.PRODUCT_BUNDLE_DESCRIPTION_KEY)
            tvBrand.text = getString(
                R.string.brand,
                bundle.getString(ProductsListViewModel.PRODUCT_BUNDLE_BRAND_KEY)
            )
            tvCategory.text = getString(
                R.string.category,
                bundle.getString(ProductsListViewModel.PRODUCT_BUNDLE_CATEGORY_KEY)
            )
            tvPrice.text = getString(
                R.string.product_price,
                bundle.getInt(ProductsListViewModel.PRODUCT_BUNDLE_PRICE_KEY).toString()
            )
            val rating = bundle.getDouble(ProductsListViewModel.PRODUCT_BUNDLE_RATING_KEY)
            Log.i("kpop", rating.toString())
            ratingBar.rating = rating.toFloat()
        }
        val images = bundle.getStringArrayList(ProductsListViewModel.PRODUCT_BUNDLE_IMAGES_KEY)
        initViewPagerAdapter(images)

    }

    private fun initViewPagerAdapter(images: List<String>?) {
        adapter = ImagesSliderAdapter(images ?: arrayListOf())
        binding.vpImages.adapter = adapter
    }

}