package com.example.productcatalog.ui.screens.products_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private lateinit var adapter: ProductsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

        initObservers()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter =
            ProductsListAdapter(appContext = requireActivity().applicationContext,
                {
                    productsOver()
                }, {position->
                    navigateToDetailScreen(position)
                })
        binding.rvProductsList.adapter = adapter
        binding.rvProductsList.layoutManager =
            GridLayoutManager(requireContext(), 2)
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
            adapter.list = it
        }
        viewModel.navigateToDetailSLE.observe(viewLifecycleOwner) {

        }
    }

    private fun initListeners() {

    }
}