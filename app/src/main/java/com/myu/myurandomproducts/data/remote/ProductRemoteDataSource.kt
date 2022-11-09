package com.myu.myurandomproducts.data.remote

import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val productService: ProductService
) : BaseDataSource(){

    suspend fun getProducts() = getResult { productService.getAllProducts()}
}