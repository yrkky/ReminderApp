package com.syrjakoyrjanai.reminderapp.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Note
import com.syrjakoyrjanai.core.domain.repository.CategoryRepository
import com.syrjakoyrjanai.core.domain.repository.NoteRepository
import com.syrjakoyrjanai.reminderapp.ui.category.CategoryViewState
import com.syrjakoyrjanai.reminderapp.ui.reminder.ReminderViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _noteViewState = MutableStateFlow<NoteViewState>(NoteViewState.Loading)
    val noteState: StateFlow<NoteViewState> = _noteViewState

    private val _selectedNote = MutableStateFlow<Note?>(null)
    private val _selectedCategory = MutableStateFlow<Category?>(null)

    private val _categoryViewState = MutableStateFlow<CategoryViewState>(CategoryViewState.Loading)
    val categoryState: StateFlow<CategoryViewState> = _categoryViewState

    private val _categoryList: MutableStateFlow<List<Category>> = MutableStateFlow(mutableListOf())
    val categories: StateFlow<List<Category>> =_categoryList

    fun onNoteSelected(note: Note) {
        _selectedNote.value = note
    }

    init {
        viewModelScope.launch {
            loadCategories()
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            noteRepository.addNote(note)
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
            _categoryViewState.value = CategoryViewState.Success(selectedCategory, categories)
            _categoryList.value = categories
        }
            .catch { error -> CategoryViewState.Error(error) }
            .launchIn(viewModelScope)
    }

    fun loadNotesFor(category: Category?) {
        if (category != null) {
            viewModelScope.launch {
                val Notes = noteRepository.loadAllNotes()
                _noteViewState.value =
                    NoteViewState.Success(
                        Notes.filter {
                            it.categoryId == category.categoryId }
                    )
            }
        }
    }

    fun loadAllNotes() {
        viewModelScope.launch {
            val Notes = noteRepository.loadAllNotes()
            _noteViewState.value =
                NoteViewState.Success(
                    Notes
                )
        }
    }
}