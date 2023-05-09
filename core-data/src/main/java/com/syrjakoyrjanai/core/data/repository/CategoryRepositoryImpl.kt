package com.syrjakoyrjanai.core.data.repository

import com.syrjakoyrjanai.core.data.datasource.category.CategoryDataSource
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dataSource: CategoryDataSource
) : CategoryRepository {
    override suspend fun addCategory(category: Category): Long = dataSource.addCategory(category)

    override suspend fun loadCategories(): Flow<List<Category>> {
        return dataSource.loadCategories()
    }

    override suspend fun loadCategory(categoryId: Long): Category = dataSource.loadCategory(categoryId)

}