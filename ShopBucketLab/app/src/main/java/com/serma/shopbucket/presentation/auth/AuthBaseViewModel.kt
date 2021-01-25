package com.serma.shopbucket.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.serma.shopbucket.data.remote.contract.NoAction
import com.serma.shopbucket.presentation.base.BaseViewModel

open class AuthBaseViewModel : BaseViewModel<NoAction>() {

    companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    private val _validationState: MutableLiveData<AuthValidationState> = MutableLiveData()
    val validationState: LiveData<AuthValidationState> = _validationState

    protected fun validate(email: String, password: String): Boolean {
        var success = false
        when {
            email.isNotEmpty() && password.length >= MIN_PASSWORD_LENGTH -> {
                _validationState.value = AuthValidationState.SUCCESS
                success = true
            }
            email.isNotEmpty() && password.length < MIN_PASSWORD_LENGTH -> _validationState.value =
                AuthValidationState.INVALID_PASSWORD
            email.isEmpty() && password.length < MIN_PASSWORD_LENGTH ->
                _validationState.value = AuthValidationState.INVALID_EMAIL
            else -> _validationState.value = AuthValidationState.INVALID_EMAIL_AND_PASSWORD
        }
        return success
    }
}