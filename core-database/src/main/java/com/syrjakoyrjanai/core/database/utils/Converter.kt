package com.syrjakoyrjanai.core.database.utils

import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converter {
    @TypeConverter
    fun toLocalDateTime(date: String): LocalDateTime = LocalDateTime.parse(date)

    @TypeConverter
    fun toDateString(date: LocalDateTime): String = date.toString()
}