package com.serma.shopbucket.di

import android.app.Application
import android.content.Context
import com.serma.shopbucket.presentation.MainActivity
import com.serma.shopbucket.presentation.purchase.PurchaseListFragment
import com.serma.shopbucket.presentation.shopping.ShoppingAddFragment
import com.serma.shopbucket.presentation.shopping.ShoppingListFragment
import com.serma.shopbucket.provider.AppProvider
import com.serma.shopbucket.provider.FacadeProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FirebaseModule::class])
interface AppComponent : FacadeProvider {

    companion object {

        private var appComponent: AppComponent? = null

        fun init(application: Application): AppComponent {
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

    fun inject(mainActivity: MainActivity)
}