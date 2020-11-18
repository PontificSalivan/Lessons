package com.serma.shopbucket.di.auth

import androidx.navigation.NavController
import com.serma.shopbucket.domain.repository.UserRepository
import com.serma.shopbucket.domain.usecase.LoginUseCase
import com.serma.shopbucket.presentation.mediator.LoginMediator
import com.serma.shopbucket.presentation.mediator.LoginMediatorImpl
import dagger.Module
import dagger.Provides

@Module
interface LoginModule {

    companion object {

        @Provides
        fun provideLoginUseCase(userRepository: UserRepository) = LoginUseCase(userRepository)

        @Provides
        fun provideLoginMediator(navController: NavController): LoginMediator =
            LoginMediatorImpl(navController)
    }
}