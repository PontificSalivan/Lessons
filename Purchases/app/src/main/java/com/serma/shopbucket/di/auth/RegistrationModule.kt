package com.serma.shopbucket.di.auth

import androidx.navigation.NavController
import com.serma.shopbucket.domain.repository.UserRepository
import com.serma.shopbucket.domain.usecase.RegistrationUseCase
import com.serma.shopbucket.domain.usecase.ValidateNicknameUseCase
import com.serma.shopbucket.presentation.mediator.RegistrationMediator
import com.serma.shopbucket.presentation.mediator.RegistrationMediatorImpl
import dagger.Module
import dagger.Provides

@Module
interface RegistrationModule {

    companion object {

        @Provides
        fun provideValidateNicknameUseCase(userRepository: UserRepository) =
            ValidateNicknameUseCase(userRepository)

        @Provides
        fun provideRegistrationUseCase(userRepository: UserRepository) =
            RegistrationUseCase(userRepository)

        @Provides
        fun provideRegistrationMediator(navController: NavController): RegistrationMediator =
            RegistrationMediatorImpl(navController)
    }
}