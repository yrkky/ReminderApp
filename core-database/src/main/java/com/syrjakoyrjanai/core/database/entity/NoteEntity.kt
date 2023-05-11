package com.syrjakoyrjanai.core.database.entity

import androidx.room.*
import java.time.LocalDateTime

@Entity(
    tableName = "notes",
    indices = [
        Index("noteId", unique = true),
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

data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long = 0,
    val title: String,
    val message: String,
    val creationTime: LocalDateTime,
    @ColumnInfo(name = "category_id")
    val categoryId: Long,
)
