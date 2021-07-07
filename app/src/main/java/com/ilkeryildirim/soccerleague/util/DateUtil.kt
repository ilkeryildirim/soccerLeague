package com.ilkeryildirim.soccerleague.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil{
    fun isInTodayRange(date: String):Boolean{
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())

        val stringDate = sdf.format(sdf.parse(date))
        println(stringDate)
        return currentDate == stringDate
    }
}