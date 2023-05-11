package com.syrjakoyrjanai.core.domain.entity

import java.time.LocalDateTime

data class Reminder(
    val reminderId: Long = 0,
    val title: String,
    val categoryId: Long,
    val reminderTime: LocalDateTime,
    val creationTime: LocalDateTime,
    val reminderSeen: LocalDateTime,
)