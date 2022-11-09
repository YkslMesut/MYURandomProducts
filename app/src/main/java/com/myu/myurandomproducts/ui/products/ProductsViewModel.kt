package com.myu.myurandomproducts.ui.products

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.myu.myurandomproducts.data.entities.ProductResponseItem
import com.myu.myurandomproducts.data.repository.ProductRepository
import com.myu.myurandomproducts.utils.Resource

class ProductsViewModel @ViewModelInject constructor(
    private val repository: ProductRepository
) : ViewModel(){
    val products : LiveData<Resource<List<ProductResponseItem>>> = repository.getProducts()
}