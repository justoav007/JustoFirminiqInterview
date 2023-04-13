package com.example.justofirminiqinterview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    val emailError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    val confirmPasswordError = MutableLiveData<String>()

    val showDialog = MutableLiveData<String>()

    fun onSubmitClicked() {
        if (isValidData()) {
            val email = email.value!!
            showDialog.value = email
        }
    }

    private fun isValidData(): Boolean {
        var isValid = true
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        if (email.value.isNullOrEmpty() || !email.value!!.matches(emailRegex.toRegex())) {
            emailError.value = "Invalid email"
            isValid = false
        } else {
            emailError.value = null
        }

        if (password.value.isNullOrEmpty() || password.value!!.length < 6) {
            passwordError.value = "Password must be at least 6 characters"
            isValid = false
        } else {
            passwordError.value = null
        }

        if (confirmPassword.value.isNullOrEmpty() || confirmPassword.value != password.value) {
            confirmPasswordError.value = "Passwords don't match"
            isValid = false
        } else {
            confirmPasswordError.value = null
        }

        return isValid
    }

}
