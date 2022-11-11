package com.myu.myurandomproducts.ui.products

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myu.myurandomproducts.data.entities.ProductResponseItem
import com.myu.myurandomproducts.data.repository.ProductRepository
import com.myu.myurandomproducts.utils.Resource
import java.lang.reflect.Type


class ProductsViewModel @ViewModelInject constructor(
    repository: ProductRepository,
    private val database: DatabaseReference,
) : ViewModel(){
    private val _responseFromDB = MutableLiveData<List<ProductResponseItem>>()
    val responseFromDB : LiveData<List<ProductResponseItem>>
        get() = _responseFromDB
    private val TAG = "ProductsViewModel"

    val products : LiveData<Resource<List<ProductResponseItem>>> = repository.getProducts()

    fun insertProductsFirebase(data: List<ProductResponseItem>) {
            data.forEach { product ->
                database.child(product.id.toString()).setValue(product).addOnSuccessListener {

                } . addOnFailureListener {
                    //Do Something
                }
            }

    }

    private val postListener = object : ValueEventListener {

        override fun onDataChange(changedData: DataSnapshot) {
            val items = changedData.children.map { changedData -> changedData.getValue(ProductResponseItem::class.java) }
            _responseFromDB.postValue(items as List<ProductResponseItem>)

        }



        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }


    fun listenRealTimeDb(){
        database.addValueEventListener(postListener)
    }
}




