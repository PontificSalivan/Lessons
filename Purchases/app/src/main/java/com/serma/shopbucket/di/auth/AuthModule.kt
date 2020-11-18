package com.serma.shopbucket.di.auth

import com.serma.shopbucket.data.remote.UserRemoteSourceImpl
import com.serma.shopbucket.data.remote.contract.UserRemoteSource
import com.serma.shopbucket.data.repository.UserRepositoryImpl
import com.serma.shopbucket.di.scope.FeatureScope
import com.serma.shopbucket.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
interface AuthModule {

    @FeatureScope
    @Binds
    fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @FeatureScope
    @Binds
    fun bindsUserRemoteSource(userRemoteSourceImpl: UserRemoteSourceImpl): UserRemoteSource

}