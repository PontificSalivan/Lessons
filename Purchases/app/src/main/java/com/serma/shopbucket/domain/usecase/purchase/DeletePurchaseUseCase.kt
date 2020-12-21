package com.serma.shopbucket.domain.usecase.purchase

import com.serma.shopbucket.data.remote.contract.PurchaseRemoteSource
import javax.inject.Inject

class DeletePurchaseUseCase @Inject constructor(
    private val purchaseRemoteSource: PurchaseRemoteSource
) {
    operator fun invoke(id: String) = purchaseRemoteSource.deletePurchase(id)
}