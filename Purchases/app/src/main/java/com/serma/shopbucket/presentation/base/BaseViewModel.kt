package com.serma.shopbucket.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.serma.shopbucket.data.Response
import com.serma.shopbucket.data.remote.contract.NoAction
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel<Type> : ViewModel() {

    protected val _delete: MutableLiveData<Response<NoAction>> =
        MutableLiveData()
    val delete: LiveData<Response<NoAction>> = _delete

    protected val _response: MutableLiveData<Response<Type>> =
        MutableLiveData()
    val response: LiveData<Response<Type>> = _response

    protected val _update: MutableLiveData<Response<NoAction>> =
        MutableLiveData()
    val update: LiveData<Response<NoAction>> = _update

    protected val _save: MutableLiveData<Response<NoAction>> =
        MutableLiveData()
    val save: LiveData<Response<NoAction>> = _save

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}