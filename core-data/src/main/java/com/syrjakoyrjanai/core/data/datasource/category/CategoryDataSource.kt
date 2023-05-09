package com.syrjakoyrjanai.core.data.datasource.category

import com.syrjakoyrjanai.core.domain.entity.Category
import kotlinx.coroutines.flow.Flow


interface CategoryDataSource {
    suspend fun addCategory(category: Category): Long
    suspend fun loadCategories(): Flow<List<Category>>
    suspend fun loadCategory(categoryId: Long): Category
}