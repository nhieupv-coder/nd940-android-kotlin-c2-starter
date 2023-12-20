package com.udacity.asteroidradar.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

private const val PATTER_FORMAT = "yyyy-MM-dd"
fun Date.convertDateToString(): String {
    val format = SimpleDateFormat(PATTER_FORMAT)
    return format.format(this)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDate(): Date = Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())

fun String.toDate(): Date {
    val dateFormat = SimpleDateFormat(PATTER_FORMAT)
    return dateFormat.parse(this) ?: Date()
}