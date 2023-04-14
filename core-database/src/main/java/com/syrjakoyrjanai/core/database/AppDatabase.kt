package com.syrjakoyrjanai.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.syrjakoyrjanai.core.database.dao.CategoryDao
import com.syrjakoyrjanai.core.database.dao.ReminderDao
import com.syrjakoyrjanai.core.database.entity.CategoryEntity
import com.syrjakoyrjanai.core.database.entity.ReminderEntity
import com.syrjakoyrjanai.core.database.utils.Converter

@Database(
    entities = [ReminderEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
    abstract fun categoryDao(): CategoryDao
}