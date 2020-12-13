package com.serma.shopbucket.di.auth

import androidx.navigation.NavController
import com.serma.shopbucket.di.scope.FeatureScope
import com.serma.shopbucket.presentation.auth.login.LoginFragment
import com.serma.shopbucket.presentation.auth.registration.RegistrationFragment
import com.serma.shopbucket.presentation.purchase.PurchaseListFragment
import com.serma.shopbucket.presentation.shopping.ShoppingAddFragment
import com.serma.shopbucket.presentation.shopping.ShoppingListFragment
import com.serma.shopbucket.provider.FacadeProvider
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    dependencies = [FacadeProvider::class],
    modules = [ViewModelFactoryModule::class]
)
interface AuthComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance navController: NavController,
            facadeProvider: FacadeProvider
        ): AuthComponent
    }

    fun inject(loginFragment: LoginFragment)

    fun inject(registrationFragment: RegistrationFragment)

    fun inject(purchaseListFragment: PurchaseListFragment)

    fun inject(shoppingListFragment: ShoppingListFragment)

    fun inject(shoppingAddFragment: ShoppingAddFragment)
}