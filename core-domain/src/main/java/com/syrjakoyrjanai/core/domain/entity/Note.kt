package com.syrjakoyrjanai.core.domain.entity

import java.time.LocalDateTime

data class Note(
    val noteId: Long = 0,
    val title: String,
    val message: String,
    val categoryId: Long,
    val creationTime: LocalDateTime,
    val creatorId: Long,
)