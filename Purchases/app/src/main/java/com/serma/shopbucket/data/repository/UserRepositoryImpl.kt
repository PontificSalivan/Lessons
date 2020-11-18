package com.serma.shopbucket.data.repository

import com.serma.shopbucket.data.Response
import com.serma.shopbucket.data.remote.AuthState
import com.serma.shopbucket.data.remote.contract.UserRemoteSource
import com.serma.shopbucket.domain.entity.User
import com.serma.shopbucket.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteSource: UserRemoteSource
) :
    UserRepository {

    override fun getUser(id: String): Single<Response<User>> = userRemoteSource.getUser(id)

    override fun login(email: String, password: String): Single<Response<AuthState>> =
        userRemoteSource.login(email, password)

    override fun registration(
        nickname: String,
        email: String,
        password: String
    ): Single<Response<AuthState>> =
        userRemoteSource.registration(nickname, email, password)

    override fun validateNickname(nickname: String): Single<Response<NicknameValidationState>> =
        userRemoteSource.validateNickname(nickname)
}