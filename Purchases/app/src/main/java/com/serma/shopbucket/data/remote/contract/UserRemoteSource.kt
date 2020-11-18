package com.serma.shopbucket.data.remote.contract

import com.serma.shopbucket.data.Response
import com.serma.shopbucket.data.remote.AuthState
import com.serma.shopbucket.domain.entity.User
import io.reactivex.rxjava3.core.Single

interface UserRemoteSource {
    fun getUser(id: String): Single<Response<User>>
    fun login(email: String, password: String): Single<Response<AuthState>>
    fun registration(email: String, password: String): Single<Response<AuthState>>
}