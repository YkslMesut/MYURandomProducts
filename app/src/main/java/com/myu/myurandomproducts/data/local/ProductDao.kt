package com.myu.myurandomproducts.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myu.myurandomproducts.data.entities.ProductResponseItem

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts() : LiveData<List<ProductResponseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductResponseItem>)

}