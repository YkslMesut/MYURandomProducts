package com.myu.myurandomproducts.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Helper
{
    fun calculateDateGiveString(insertedAt: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        val date = simpleDateFormat.parse(insertedAt)
        val now = simpleDateFormat.parse(currentDateAsString())
        val diff = date.time - now.time

        val simpleDateFormatResult = SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault())
        return  simpleDateFormatResult.format(diff)

    }

    private fun currentDateAsString(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    }

    fun getCurrentTimeInMillis(insertedAt: String) : Long{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

       return simpleDateFormat.parse(insertedAt).time
    }

}