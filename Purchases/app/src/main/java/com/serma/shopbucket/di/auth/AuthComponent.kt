package com.serma.shopbucket.di.auth

import android.app.Application
import com.serma.shopbucket.di.AppComponent
import com.serma.shopbucket.di.scope.FeatureScope
import com.serma.shopbucket.presentation.auth.login.LoginFragment
import com.serma.shopbucket.presentation.auth.registration.RegistrationFragment
import com.serma.shopbucket.provider.FacadeProvider
import dagger.Component

@FeatureScope
@Component(
    dependencies = [FacadeProvider::class],
    modules = [AuthModule::class]
)
interface AuthComponent {

    companion object {

        private var authComponent: AuthComponent? = null

        fun init(application: Application): AuthComponent { // ин6ициализация компонента
            return authComponent ?: DaggerAuthComponent
                .factory()
                .create(AppComponent.init(application))
                .also {
                    authComponent = it
                }
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            facadeProvider: FacadeProvider
        ): AuthComponent
    }

    fun inject(loginFragment: LoginFragment)

    fun inject(registrationFragment: RegistrationFragment)
}