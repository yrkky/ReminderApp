package com.syrjakoyrjanai.reminderapp.ui.reminder

import com.syrjakoyrjanai.core.domain.entity.Reminder

sealed interface ReminderViewState {
    object Loading: ReminderViewState
    data class Success(
        val data: List<Reminder>
    ): ReminderViewState
}
