package com.syrjakoyrjanai.reminderapp.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow<CategoryViewState>(CategoryViewState.Loading)
    val uiState: StateFlow<CategoryViewState> = _viewState

    private val _selectedCategory = MutableStateFlow<Category?>(null)

    fun onCategorySelected(category: Category) {
        _selectedCategory.value = category
    }

    init {
        fakeData().forEach {
            addCategory(it)
        }
        viewModelScope.launch {
            loadCategories()
        }
    }

    private fun addCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.addCategory(category)
        }
    }

    private suspend fun loadCategories() {
        combine(
            categoryRepository.loadCategories()
                .onEach { categories ->
                    if (categories.isNotEmpty() && _selectedCategory.value == null) {
                        _selectedCategory.value = categories.first()
                    }
                },
            _selectedCategory
        ) { categories, selectedCategory ->
            _viewState.value = CategoryViewState.Success(selectedCategory, categories)
        }
            .catch { error -> CategoryViewState.Error(error) }
            .launchIn(viewModelScope)
    }
}