package com.serma.shopbucket.di.purchase

import android.app.Application
import com.serma.shopbucket.di.AppComponent
import com.serma.shopbucket.di.scope.FeatureScope
import com.serma.shopbucket.presentation.purchase.PurchaseAddFragment
import com.serma.shopbucket.presentation.purchase.PurchaseListFragment
import com.serma.shopbucket.provider.FacadeProvider
import dagger.Component

@Component(dependencies = [FacadeProvider::class], modules = [PurchaseModule::class])
@FeatureScope
interface PurchaseComponent {

    companion object {

        private var purchaseComponent: PurchaseComponent? = null

        fun init(application: Application): PurchaseComponent {
            return purchaseComponent ?: DaggerPurchaseComponent
                .factory()
                .create(AppComponent.init(application))
                .also {
                    purchaseComponent = it
                }
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            facadeProvider: FacadeProvider
        ): PurchaseComponent
    }

    fun inject(purchaseListFragment: PurchaseListFragment)

    fun inject(purchaseAddFragment: PurchaseAddFragment)
}