package com.example.productcatalog.ui.screens.products_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productcatalog.R
import com.example.productcatalog.databinding.FragmentProductsListBinding
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

        initObservers()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        productsAdapter =
            ProductsListAdapter(appContext = requireActivity().applicationContext,
                {
                    productsOver()
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
        binding.spinnerCategories.setSelection(0, false)
        binding.spinnerCategories.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    // catsListViewModel.breedChoose = catsListViewModel.idsCats[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //   catsListViewModel.breedChoose = ""
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

    private fun productsOver() {
        viewModel.getNewProducts()
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
    }

    private fun showContent(flag: Boolean) {
        binding.progressBar.isVisible = !flag
        binding.tvError.isVisible = !flag
        binding.contentContainer.isVisible = flag
    }

    private fun showError(flag: Boolean) {
        binding.progressBar.isVisible = !flag
        binding.tvError.isVisible = flag
        binding.contentContainer.isVisible = !flag
    }
}