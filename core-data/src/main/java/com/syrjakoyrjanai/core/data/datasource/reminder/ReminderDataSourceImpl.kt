package com.syrjakoyrjanai.core.data.datasource.reminder

import com.syrjakoyrjanai.core.database.dao.ReminderDao
import com.syrjakoyrjanai.core.database.entity.ReminderEntity
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class ReminderDataSourceImpl @Inject constructor(
    private val reminderDao: ReminderDao
) : ReminderDataSource {

    override suspend fun addReminder(reminder: Reminder) {
        reminderDao.insertOrUpdate(reminder.toEntity())
    }

    override suspend fun editReminder(reminder: Reminder) {
        reminderDao.insertOrUpdate(reminder.toEntity())
    }

    override suspend fun loadRemindersFor(category: Category): Flow<List<Reminder>> {
        return reminderDao.findRemindersByCategory(category.categoryId).map { list ->
            list.map {
                it.fromEntity()
            }
        }
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.delete(reminder.toEntity())
    }

    override suspend fun loadAllReminders(): List<Reminder> {
        return reminderDao.findAll().map {
            it.fromEntity()
        }
    }

    private fun Reminder.toEntity() = ReminderEntity(
        reminderId = this.reminderId,
        title = this.title,
        reminderTime = this.reminderTime,
        creationTime = this.creationTime,
        categoryId = this.categoryId,
        reminderSeen = this.reminderSeen,)

    private fun ReminderEntity.fromEntity() = Reminder(
        reminderId = this.reminderId,
        title = this.title,
        reminderTime = this.reminderTime,
        creationTime = this.creationTime,
        categoryId = this.categoryId,
        reminderSeen = this.reminderSeen,
    )

}
