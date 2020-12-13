package com.serma.shopbucket.di.auth

import androidx.lifecycle.ViewModel
import com.serma.shopbucket.di.key.ViewModelKey
import com.serma.shopbucket.presentation.auth.login.LoginViewModel
import com.serma.shopbucket.presentation.auth.registration.RegistrationViewModel
import com.serma.shopbucket.presentation.purchase.PurchaseViewModel
import com.serma.shopbucket.presentation.shopping.ShoppingViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(PurchaseViewModel::class)
    fun bindsPurchaseViewModelFactory(
        purchaseViewModel: PurchaseViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShoppingViewModel::class)
    fun bindsShoppingViewModelFactory(
        purchaseViewModel: ShoppingViewModel
    ): ViewModel
}