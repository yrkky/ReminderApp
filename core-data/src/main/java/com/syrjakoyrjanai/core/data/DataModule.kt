package com.syrjakoyrjanai.core.data

import com.yrkky.core.data.datasource.category.CategoryDataSource
import com.yrkky.core.data.datasource.category.CategoryDataSourceImpl
import com.yrkky.core.data.datasource.reminder.ReminderDataSource
import com.yrkky.core.data.datasource.reminder.ReminderDataSourceImpl
import com.yrkky.core.data.repository.CategoryRepositoryImpl
import com.yrkky.core.data.repository.ReminderRepositoryImpl
import com.yrkky.core.domain.repository.CategoryRepository
import com.yrkky.core.domain.repository.ReminderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindReminderDataSource(
        reminderDataSource: ReminderDataSourceImpl
    ): ReminderDataSource

    @Singleton
    @Binds
    fun bindReminderRepository(
        reminderRepository: ReminderRepositoryImpl
    ): ReminderRepository

    @Singleton
    @Binds
    fun bindCategoryDataSource(
        categoryDataSource: CategoryDataSourceImpl
    ): CategoryDataSource

    @Singleton
    @Binds
    fun bindCategoryRepository(
        categoryRepository: CategoryRepositoryImpl
    ): CategoryRepository

}