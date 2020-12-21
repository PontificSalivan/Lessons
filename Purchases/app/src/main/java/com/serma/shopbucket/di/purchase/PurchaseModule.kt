package com.serma.shopbucket.di.purchase

import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.serma.shopbucket.data.remote.PurchaseRemoteSourceImpl
import com.serma.shopbucket.data.remote.contract.PurchaseRemoteSource
import com.serma.shopbucket.di.key.ViewModelKey
import com.serma.shopbucket.di.scope.FeatureScope
import com.serma.shopbucket.domain.entity.Purchase
import com.serma.shopbucket.presentation.purchase.PurchaseViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface PurchaseModule {

    companion object {
        @Provides
        @FeatureScope
        fun provideQuery(firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth) =
            FirestoreRecyclerOptions.Builder<Purchase>()
                .setQuery(
                    firebaseFirestore.collection("purchase")
                        .whereEqualTo("user_uid", firebaseAuth.uid),
                    Purchase::class.java
                )
                .build()
    }

    @Binds
    @IntoMap
    @ViewModelKey(PurchaseViewModel::class)
    fun bindsPurchaseViewModelFactory(
        purchaseViewModel: PurchaseViewModel
    ): ViewModel

    @Binds
    fun bindsPurchaseRemoteSource(purchaseRemoteSourceImpl: PurchaseRemoteSourceImpl): PurchaseRemoteSource

}