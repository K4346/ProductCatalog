package com.example.productcatalog.ui.screens.products_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productcatalog.MainActivity
import com.example.productcatalog.R
import com.example.productcatalog.databinding.FragmentProductsListBinding
import com.example.productcatalog.domain.entities.ProductsShowType
import com.example.productcatalog.domain.entities.ProductsShowType.Category
import com.example.productcatalog.domain.entities.ProductsShowType.Normal
import com.example.productcatalog.domain.entities.ProductsShowType.Search
import com.example.productcatalog.ui.adpaters.ProductsListAdapter

class ProductsListFragment : Fragment() {

    private var _binding: FragmentProductsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsListViewModel by viewModels()

    private lateinit var productsAdapter: ProductsListAdapter

    private lateinit var categoryAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar()

        initObservers()

        initRecyclerView()

        initListeners()
        initSearchListener()
    }

    private fun initToolBar() {
        val title = getString(R.string.product_catalog)
        (requireActivity() as MainActivity).initToolBar(title, false)
    }

    private fun initListeners() {
        binding.refreshButton.setOnClickListener {
            productsNeedUpdate(needRestart = true)
        }
    }

    private fun initSearchListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0?.isEmpty() == true) binding.searchView.clearFocus()
                if (p0 != null) viewModel.onUpdatedSearchText(p0)
                return true
            }
        })
    }

    private fun initRecyclerView() {
        productsAdapter =
            ProductsListAdapter(appContext = requireActivity().applicationContext,
                {
                    productsNeedUpdate(needRestart = false)
                }, { position ->
                    navigateToDetailScreen(position)
                })
        binding.rvProductsList.adapter = productsAdapter
        binding.rvProductsList.layoutManager =
            GridLayoutManager(requireContext(), 2)
    }

    private fun initCategoriesAdapter(categories: List<String>) {
        categoryAdapter = spinnerAdapterMake(categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategories.adapter = categoryAdapter
        binding.spinnerCategories.setSelection(viewModel.currentCategoryPosition, true)
        binding.spinnerCategories.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel.changeCategory(p2)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }

    private fun spinnerAdapterMake(categories: List<String>): ArrayAdapter<String> {
        return ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        )

    }

    private fun navigateToDetailScreen(position: Int) {
        findNavController().navigate(
            R.id.action_productsListFragment_to_productDetailFragment,
            viewModel.getBundleForDetailScreen(position)
        )
    }

    private fun productsNeedUpdate(needRestart: Boolean = false) {
        viewModel.getNewProducts(clearFlag = needRestart)
    }

    private fun initObservers() {
        viewModel.productsMLE.observe(viewLifecycleOwner) {
            showContent(true)
            productsAdapter.list = it
        }
        viewModel.categoriesMLE.observe(viewLifecycleOwner) {
            initCategoriesAdapter(it)
        }
        viewModel.showError.observe(viewLifecycleOwner) {
            showError(true)
        }
        viewModel.currentProductsShowTypeMLE.observe(viewLifecycleOwner) {
            updateProductsTypeState(it)
        }
    }

    private fun updateProductsTypeState(productsShowType: ProductsShowType) {
        when (productsShowType) {
            Normal -> {
                binding.searchView.isInvisible = false
                binding.spinnerCategories.isInvisible = false
            }

            Search -> {
                binding.searchView.isInvisible = false
                binding.spinnerCategories.isInvisible = true
            }

            Category -> {
                binding.searchView.isInvisible = true
                binding.spinnerCategories.isInvisible = false
            }
        }
    }

    private fun showContent(flag: Boolean) {
        binding.progressBar.isVisible = !flag
        binding.containerError.isVisible = !flag
        binding.containerSearchAndFilter.isVisible = flag
        binding.rvProductsList.isVisible = flag
    }

    private fun showError(flag: Boolean) {
        binding.progressBar.isVisible = !flag
        binding.containerError.isVisible = flag
        binding.containerSearchAndFilter.isVisible = !flag
        binding.rvProductsList.isVisible = !flag
    }
}