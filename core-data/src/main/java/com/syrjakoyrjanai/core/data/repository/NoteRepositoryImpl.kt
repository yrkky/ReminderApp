package com.syrjakoyrjanai.core.data.repository

import com.syrjakoyrjanai.core.data.datasource.note.NoteDataSource
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Note
import com.syrjakoyrjanai.core.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDataSource: NoteDataSource
): NoteRepository {
    override suspend fun addNote(note: Note) {
        noteDataSource.addNote(note)
    }

    override suspend fun loadNotesFor(category: Category): Flow<List<Note>> {
        return noteDataSource.loadNotesFor(category)
    }

    override suspend fun editNote(note: Note) {
        noteDataSource.editNote(note)
    }

    override suspend fun loadAllNotes(): List<Note> {
        return noteDataSource.loadAllNotes()
    }

    override suspend fun deleteNote(note: Note) {
        noteDataSource.deleteNote(note)
    }

}