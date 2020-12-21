package com.serma.shopbucket.domain.usecase.purchase

import com.serma.shopbucket.data.remote.contract.PurchaseRemoteSource
import javax.inject.Inject

class GetPurchaseUseCase @Inject constructor(private val purchaseRemoteSource: PurchaseRemoteSource) {
    operator fun invoke(id: String) = purchaseRemoteSource.getPurchase(id)
}