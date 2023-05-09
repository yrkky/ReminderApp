package com.syrjakoyrjanai.core.data.datasource.reminder

import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderDataSource {
    suspend fun addReminder(reminder: Reminder)
    suspend fun editReminder(reminder: Reminder)
    suspend fun loadRemindersFor(category: Category): Flow<List<Reminder>>

    suspend fun loadAllReminders(): List<Reminder>

    suspend fun deleteReminder(reminder: Reminder)

}