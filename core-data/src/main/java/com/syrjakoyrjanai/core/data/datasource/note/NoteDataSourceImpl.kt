package com.syrjakoyrjanai.core.data.datasource.note

import com.syrjakoyrjanai.core.data.datasource.note.NoteDataSource
import com.syrjakoyrjanai.core.database.dao.NoteDao
import com.syrjakoyrjanai.core.database.entity.NoteEntity
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class NoteDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteDataSource {

    override suspend fun addNote(note: Note) {
        noteDao.insertOrUpdate(note.toEntity())
    }

    override suspend fun editNote(note: Note) {
        noteDao.insertOrUpdate(note.toEntity())
    }

    override suspend fun loadNotesFor(category: Category): Flow<List<Note>> {
        return noteDao.findNotesByCategory(category.categoryId).map { list ->
            list.map {
                it.fromEntity()
            }
        }
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.delete(note.toEntity())
    }

    override suspend fun loadAllNotes(): List<Note> {
        return noteDao.findAll().map {
            it.fromEntity()
        }
    }

    private fun Note.toEntity() = NoteEntity(
        noteId = this.noteId,
        title = this.title,
        message = this.message,
        creationTime = this.creationTime,
        creatorId = this.creatorId,
        categoryId = this.categoryId,
    )

    private fun NoteEntity.fromEntity() = Note(
        noteId = this.noteId,
        title = this.title,
        message = this.message,
        creationTime = this.creationTime,
        categoryId = this.categoryId,
        creatorId = this.creatorId,
    )

}