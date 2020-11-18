package com.serma.shopbucket.di.auth

import androidx.lifecycle.ViewModel
import com.serma.shopbucket.di.key.ViewModelKey
import com.serma.shopbucket.presentation.auth.login.LoginViewModel
import com.serma.shopbucket.presentation.auth.registration.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelFactoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindsLoginViewModelFactory(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindsRegistrationViewModelFactory(
        registrationViewModel: RegistrationViewModel
    ): ViewModel

}