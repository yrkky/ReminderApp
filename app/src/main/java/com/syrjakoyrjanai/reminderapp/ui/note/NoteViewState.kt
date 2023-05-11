package com.syrjakoyrjanai.reminderapp.ui.note

import com.syrjakoyrjanai.core.domain.entity.Note

sealed interface NoteViewState {
    object Loading: NoteViewState
    data class Success(
        val data: List<Note>
    ): NoteViewState
}
