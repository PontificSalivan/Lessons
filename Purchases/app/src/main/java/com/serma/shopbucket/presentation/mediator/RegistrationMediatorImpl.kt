package com.serma.shopbucket.presentation.mediator

import androidx.navigation.NavController
import com.serma.shopbucket.R

class RegistrationMediatorImpl(private val navController: NavController): RegistrationMediator {
    override fun openLoginScreen() {
        navController.navigate(R.id.loginFragment)
    }
}