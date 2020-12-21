package com.serma.shopbucket.domain.usecase.shopping

import com.serma.shopbucket.data.remote.contract.ShoppingRemoteSource
import javax.inject.Inject

class SaveShoppingUseCase @Inject constructor(private val shoppingRemoteSource: ShoppingRemoteSource) {
    operator fun invoke(purchaseId: String, name: String, count: Long, price: Long) =
        shoppingRemoteSource.saveShopping(purchaseId, name, count, price)
}