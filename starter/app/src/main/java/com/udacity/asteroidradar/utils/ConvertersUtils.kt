package com.udacity.asteroidradar.utils

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
