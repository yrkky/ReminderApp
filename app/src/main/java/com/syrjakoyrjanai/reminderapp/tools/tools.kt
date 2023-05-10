package com.syrjakoyrjanai.reminderapp.tools


/**
 * Checks if the email is in valid form
 */
fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
    return emailRegex.matches(this)
}


/**
 * Checks if the length is sufficient
 */
fun String.hasEnoughLength(lengthCheck: Int): Boolean {
    return this.length >= lengthCheck
}
