package com.syrjakoyrjanai.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrjakoyrjanai.core.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE noteId LIKE :noteId")
    fun findOne(noteId: Long): Flow<NoteEntity>

    @Query("SELECT * FROM notes")
    suspend fun findAll(): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE category_id LIKE :categoryId")
    fun findNotesByCategory(categoryId: Long): Flow<List<NoteEntity>>

    @Delete()
    suspend fun delete(note: NoteEntity)
}