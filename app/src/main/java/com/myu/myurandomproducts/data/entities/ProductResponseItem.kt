package com.myu.myurandomproducts.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class ProductResponseItem (
    @SerializedName("category")
    val category: String ="",
    @SerializedName("description")
    val description: String="",
    @PrimaryKey
    @SerializedName("id")
    val id: Int=0,
    @SerializedName("image")
    val image: String ="",
    @SerializedName("price")
    val price: Double = 0.0,
    @SerializedName("title")
    val title: String =""
)