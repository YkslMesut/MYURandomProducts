package com.myu.myurandomproducts.ui.products

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.myu.myurandomproducts.data.entities.ProductResponseItem
import com.myu.myurandomproducts.data.repository.ProductRepository
import com.myu.myurandomproducts.utils.Resource
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

class ProductsViewModel @ViewModelInject constructor(
    repository: ProductRepository,
    private val database: DatabaseReference,
) : ViewModel(),ValueEventListener{

    private val _responseFromDB = MutableLiveData<List<ProductResponseItem>>()
    val responseFromDB : LiveData<List<ProductResponseItem>>
        get() = _responseFromDB

    val products : LiveData<Resource<List<ProductResponseItem>>> = repository.getProducts()

    fun insertProductsFirebase(data: List<ProductResponseItem>) {
        data.forEach { product ->
                database.child(product.id.toString()).setValue(product).addOnSuccessListener {
                } . addOnFailureListener {
                    //Do Something For fail case
                }
            }

    }

    fun listenDB(){
        database.addValueEventListener(this)
    }

    override fun onDataChange(changedData: DataSnapshot) {
        val items = changedData.children.map { changedData -> changedData.getValue(ProductResponseItem::class.java) }
        _responseFromDB.postValue(items as List<ProductResponseItem>)
    }

    override fun onCancelled(error: DatabaseError) {

    }


    private fun generateRandomDate(): String {
        val c = Calendar.getInstance()

        val localYear = c.get(Calendar.YEAR)
        val localMonth = c.get(Calendar.MONTH)
        val localDay = c.get(Calendar.DAY_OF_MONTH)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val randomDate = LocalDateTime.of(localYear,localMonth,localDay,hour,minute)
            .plusDays(Random.nextLong(365)).plusHours(Random.nextLong(24))
            .plusMinutes(Random.nextLong(60)).plusSeconds(Random.nextLong(60))


       return randomDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }

    fun addItemRemainingTime (data: List<ProductResponseItem>) : List<ProductResponseItem>{
        data.forEach { product ->
            product.remaining_time = generateRandomDate()
        }
        return data
    }



}




