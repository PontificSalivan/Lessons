package com.serma.dionysusproject.di

import android.app.Application
import android.content.Context
import com.serma.shopbucket.di.auth.AppModule
import com.serma.shopbucket.di.auth.ViewModelFactoryModule
import com.serma.shopbucket.presentation.purchase.PurchaseListFragment
import com.serma.shopbucket.presentation.shopping.ShoppingAddFragment
import com.serma.shopbucket.presentation.shopping.ShoppingListFragment
import com.serma.shopbucket.provider.AppProvider
import com.serma.shopbucket.provider.FacadeProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FirebaseModule::class, ViewModelFactoryModule::class, AppModule::class])
interface AppComponent : FacadeProvider {

    companion object {

        private var appComponent: AppProvider? = null

        fun create(application: Application): AppProvider {
            return appComponent ?: DaggerAppComponent
                .factory()
                .create(application.applicationContext)
                .also {
                    appComponent = it
                }
        }
    }

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(purchaseListFragment: PurchaseListFragment)

    fun inject(shoppingListFragment: ShoppingListFragment)

    fun inject(shoppingAddFragment: ShoppingAddFragment)
}