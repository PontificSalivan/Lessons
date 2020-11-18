package com.serma.shopbucket.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.serma.shopbucket.data.Failure
import com.serma.shopbucket.data.Success
import com.serma.shopbucket.data.remote.AuthState
import com.serma.shopbucket.domain.usecase.LoginUseCase
import com.serma.shopbucket.presentation.auth.AuthBaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : AuthBaseViewModel() {

    private val _loginState: MutableLiveData<AuthState> =
        MutableLiveData()
    val loginState: LiveData<AuthState> = _loginState

    fun login(email: String, password: String) {
        if (validate(email, password)) {
            val disposable = loginUseCase(email, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    when (response) {
                        is Success -> _loginState.value = response.result
                        is Failure -> _loginState.value =
                            AuthState.FAILURE
                    }
                }
            compositeDisposable.add(disposable)
        }
    }

}