package com.serma.shopbucket.data.remote.contract

import io.reactivex.rxjava3.core.Single

interface AuthRemoteSource {
    fun registration(email: String, password: String): Single<AuthState>
    fun login(email: String, password: String): Single<AuthState>
}

enum class AuthState {
    SUCCESS, FAILURE
}