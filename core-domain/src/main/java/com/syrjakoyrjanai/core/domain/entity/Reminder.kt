package com.syrjakoyrjanai.core.domain.entity

import java.time.LocalDateTime

data class Reminder(
    val reminderId: Long = 0,
    val title: String,
    val message: String,
    val categoryId: Long,
    val location_x: Double,
    val location_y: Double,
    val reminderTime: LocalDateTime,
    val creationTime: LocalDateTime,
    val creatorId: Long,
    val reminderSeen: LocalDateTime,
    val icon: String,
    )
