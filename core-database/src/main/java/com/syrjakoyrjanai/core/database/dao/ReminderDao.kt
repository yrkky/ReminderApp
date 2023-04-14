package com.yrkky.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yrkky.core.database.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(reminder: ReminderEntity)

    @Query("SELECT * FROM reminders WHERE reminderId LIKE :reminderId")
    fun findOne(reminderId: Long): Flow<ReminderEntity>

    @Query("SELECT * FROM reminders")
    suspend fun findAll(): List<ReminderEntity>

    @Query("SELECT * FROM reminders WHERE category_id LIKE :categoryId")
    fun findRemindersByCategory(categoryId: Long): Flow<List<ReminderEntity>>

    @Delete()
    suspend fun delete(reminder: ReminderEntity)
}