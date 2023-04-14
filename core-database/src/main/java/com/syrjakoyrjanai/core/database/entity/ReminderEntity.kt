package com.syrjakoyrjanai.core.database.entity

import androidx.room.*
import java.time.LocalDateTime

@Entity(
    tableName = "reminders",
    indices = [
        Index("reminderId", unique = true),
        Index("category_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["category_id"],
            onUpdate = ForeignKey.CASCADE, // when a category is updated, we update all associated reminders
            onDelete = ForeignKey.CASCADE // when a category is deleted, we delete all reminders associated with it
        )
    ]
)

data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val reminderId: Long = 0,
    val title: String,
    val message: String,
    val location_x: Double,
    val location_y: Double,
    val reminderTime: LocalDateTime,
    val creationTime: LocalDateTime,
    val creatorId: Long,
    @ColumnInfo(name = "category_id")
    val categoryId: Long,
    val reminderSeen: LocalDateTime,
    val icon: String,
)
