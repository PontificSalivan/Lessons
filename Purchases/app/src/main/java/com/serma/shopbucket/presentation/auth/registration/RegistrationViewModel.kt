package com.serma.shopbucket.presentation.auth.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.serma.shopbucket.data.Failure
import com.serma.shopbucket.data.Success
import com.serma.shopbucket.data.remote.AuthState
import com.serma.shopbucket.domain.usecase.RegistrationUseCase
import com.serma.shopbucket.presentation.auth.AuthBaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
) :
    AuthBaseViewModel() {

    private val _registrationState: MutableLiveData<AuthState> =
        MutableLiveData()
    val registrationState: LiveData<AuthState> = _registrationState

    fun registration(email: String, password: String) {
        if (validate(email, password)) {
            val disposable = registrationUseCase(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    when (response) {
                        is Success -> _registrationState.value = response.result
                        is Failure -> _registrationState.value =
                            AuthState.FAILURE
                    }
                }
            compositeDisposable.add(disposable)
        }
    }
}