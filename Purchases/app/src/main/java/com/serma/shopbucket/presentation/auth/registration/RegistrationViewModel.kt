package com.serma.shopbucket.presentation.auth.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.serma.shopbucket.data.remote.contract.AuthState
import com.serma.shopbucket.domain.usecase.auth.RegistrationUseCase
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
                .subscribe { result ->
                    _registrationState.value = result
                }
            compositeDisposable.add(disposable)
        }
    }

}