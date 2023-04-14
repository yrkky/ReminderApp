package com.syrjakoyrjanai.core.domain.entity

import androidx.annotation.Keep

@Keep
data class Category(
    val categoryId: Long = 0,
    val name: String
)

