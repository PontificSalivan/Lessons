package com.serma.shopbucket.domain.usecase.purchase

import com.serma.shopbucket.data.remote.contract.PurchaseRemoteSource
import com.serma.shopbucket.domain.entity.Purchase
import javax.inject.Inject

class SavePurchaseUseCase @Inject constructor(private val purchaseRemoteSource: PurchaseRemoteSource) {
    operator fun invoke(name: String) = purchaseRemoteSource.savePurchase(name)
}