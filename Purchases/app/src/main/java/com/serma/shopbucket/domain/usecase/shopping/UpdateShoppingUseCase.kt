package com.serma.shopbucket.domain.usecase.shopping

import com.serma.shopbucket.data.remote.contract.PurchaseRemoteSource
import com.serma.shopbucket.data.remote.contract.ShoppingRemoteSource
import javax.inject.Inject

class UpdateShoppingUseCase @Inject constructor(private val shoppingRemoteSource: ShoppingRemoteSource) {
    operator fun invoke(
        purchaseId: String,
        shoppingId: String,
        name: String,
        count: Long,
        price: Long
    ) = shoppingRemoteSource.updateShopping(purchaseId, shoppingId, name, count, price)

    operator fun invoke(
        purchaseId: String,
        shoppingId: String,
        complete: Boolean
    ) = shoppingRemoteSource.updateShopping(purchaseId, shoppingId, complete)
}