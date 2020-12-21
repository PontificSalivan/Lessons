package com.serma.shopbucket.domain.usecase.purchase

import com.serma.shopbucket.data.remote.contract.PurchaseRemoteSource
import javax.inject.Inject

class UpdatePurchaseUseCase @Inject constructor(private val purchaseRemoteSource: PurchaseRemoteSource) {
    operator fun invoke(id: String, name: String) = purchaseRemoteSource.updatePurchase(id, name)
}