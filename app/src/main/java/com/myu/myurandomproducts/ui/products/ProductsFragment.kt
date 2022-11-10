package com.myu.myurandomproducts.ui.products

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myu.myurandomproducts.R
import com.myu.myurandomproducts.databinding.FragmentProductsBinding
import com.myu.myurandomproducts.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class ProductsFragment : Fragment() , ProductAdapter.ProductItemListener {

    private val TAG = "ProductsFragment"
    private var _binding : FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : ProductsViewModel by viewModels()
    private lateinit var adapter : ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(this)
        binding.productsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.productsRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.products.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    if (!response.data.isNullOrEmpty()) adapter.setProducts(ArrayList(response.data))
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(activity, response.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {

                }
            }
        }
    }

    override fun onClickedProduct(productId: Int) {
        Log.d(TAG, "onClickedProduct: $productId")
    }

}