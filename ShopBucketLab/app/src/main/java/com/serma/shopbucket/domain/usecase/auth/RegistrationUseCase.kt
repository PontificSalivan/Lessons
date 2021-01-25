package com.serma.shopbucket.domain.usecase.auth

import com.serma.shopbucket.data.remote.contract.AuthRemoteSource
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val authRemoteSource: AuthRemoteSource) {
    operator fun invoke(email: String, password: String) =
        authRemoteSource.registration(email, password)
}