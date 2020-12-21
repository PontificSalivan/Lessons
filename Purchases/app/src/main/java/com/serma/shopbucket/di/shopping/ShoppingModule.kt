package com.serma.shopbucket.di.shopping

import androidx.lifecycle.ViewModel
import com.serma.shopbucket.data.remote.ShoppingRemoteSourceImpl
import com.serma.shopbucket.data.remote.contract.ShoppingRemoteSource
import com.serma.shopbucket.di.key.ViewModelKey
import com.serma.shopbucket.presentation.shopping.ShoppingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ShoppingModule {

    @Binds
    @IntoMap
    @ViewModelKey(ShoppingViewModel::class)
    fun bindsShoppingViewModelFactory(
        purchaseViewModel: ShoppingViewModel
    ): ViewModel

    @Binds
    fun bindsShoppingRemoteSource(shoppingRemoteSourceImpl: ShoppingRemoteSourceImpl): ShoppingRemoteSource
}