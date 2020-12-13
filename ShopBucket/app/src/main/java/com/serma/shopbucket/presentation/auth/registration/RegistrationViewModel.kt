package com.serma.shopbucket.presentation.auth.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.serma.shopbucket.data.AuthState
import com.serma.shopbucket.presentation.auth.AuthBaseViewModel
import javax.inject.Inject


class RegistrationViewModel @Inject constructor(
) :
    AuthBaseViewModel() {

    private val _registrationState: MutableLiveData<AuthState> =
        MutableLiveData()
    val registrationState: LiveData<AuthState> = _registrationState

    fun registration(email: String, password: String) {
        _registrationState.value = AuthState.SUCCESS
    }

}