package com.myu.myurandomproducts.data.remote

import com.myu.myurandomproducts.data.entities.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {

    @GET(Constants.PRODUCTS)
    suspend fun getAllProducts() : Response<ProductResponse>

}