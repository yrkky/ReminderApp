package com.syrjakoyrjanai.core.domain.repository

import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNote(note: Note)
    suspend fun editNote(note: Note)
    suspend fun loadNotesFor(category: Category): Flow<List<Note>>
    suspend fun loadAllNotes(): List<Note>
    suspend fun deleteNote(note: Note)
}