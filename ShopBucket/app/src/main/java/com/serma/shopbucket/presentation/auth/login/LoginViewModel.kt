package com.serma.shopbucket.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.serma.shopbucket.data.AuthState
import com.serma.shopbucket.presentation.auth.AuthBaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor() :
    AuthBaseViewModel() {

    private val _loginState: MutableLiveData<AuthState> =
        MutableLiveData()
    val loginState: LiveData<AuthState> = _loginState

    fun login(email: String, password: String) {
        _loginState.value = AuthState.SUCCESS
    }
}