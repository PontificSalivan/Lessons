package com.serma.shopbucket.di.shopping

import android.app.Application
import com.serma.shopbucket.di.AppComponent
import com.serma.shopbucket.di.scope.FeatureScope
import com.serma.shopbucket.presentation.shopping.ShoppingAddFragment
import com.serma.shopbucket.presentation.shopping.ShoppingListFragment
import com.serma.shopbucket.provider.FacadeProvider
import dagger.Component

@Component(dependencies = [FacadeProvider::class], modules = [ShoppingModule::class])
@FeatureScope
interface ShoppingComponent {

    companion object {

        private var shoppingComponent: ShoppingComponent? = null

        fun init(application: Application): ShoppingComponent {
            return shoppingComponent ?: DaggerShoppingComponent
                .factory()
                .create(AppComponent.init(application))
                .also {
                    shoppingComponent = it
                }
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            facadeProvider: FacadeProvider
        ): ShoppingComponent
    }

    fun inject(shoppingListFragment: ShoppingListFragment)

    fun inject(shoppingAddFragment: ShoppingAddFragment)
}