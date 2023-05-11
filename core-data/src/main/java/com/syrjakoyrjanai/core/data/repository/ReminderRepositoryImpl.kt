package com.syrjakoyrjanai.core.data.repository

import com.syrjakoyrjanai.core.data.datasource.reminder.ReminderDataSource
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Reminder
import com.syrjakoyrjanai.core.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDataSource: ReminderDataSource
): ReminderRepository {
    override suspend fun addReminder(reminder: Reminder) {
        reminderDataSource.addReminder(reminder)
    }

    override suspend fun loadRemindersFor(category: Category): Flow<List<Reminder>> {
        return reminderDataSource.loadRemindersFor(category)
    }

    override suspend fun editReminder(reminder: Reminder) {
        reminderDataSource.editReminder(reminder)
    }

    override suspend fun loadAllReminders(): List<Reminder> {
        return reminderDataSource.loadAllReminders()
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDataSource.deleteReminder(reminder)
    }

}