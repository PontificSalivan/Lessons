package com.serma.shopbucket.domain.usecase.auth

import com.serma.shopbucket.data.remote.contract.AuthRemoteSource
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRemoteSource: AuthRemoteSource) {
    operator fun invoke(email: String, password: String) = authRemoteSource.login(email, password)
}