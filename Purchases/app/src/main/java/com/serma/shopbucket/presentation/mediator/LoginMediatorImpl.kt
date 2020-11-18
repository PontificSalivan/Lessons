package com.serma.shopbucket.presentation.mediator

import androidx.navigation.NavController
import com.serma.shopbucket.R

class LoginMediatorImpl(private val navController: NavController): LoginMediator {
    override fun openRegistrationScreen() {
        navController.navigate(R.id.registrationFragment)
    }
}