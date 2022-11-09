package com.myu.myurandomproducts.data.repository

import com.myu.myurandomproducts.data.local.ProductDao
import com.myu.myurandomproducts.data.remote.ProductRemoteDataSource
import com.myu.myurandomproducts.utils.performGetOperation
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductDao
) {

    fun getProducts() = performGetOperation(
        databaseQuery = { localDataSource.getAllProducts() },
        networkCall = { remoteDataSource.getProducts() },
        saveCallResult = { localDataSource.insertAll(it) }
    )
}