package com.serma.shopbucket.domain.usecase.shopping

import com.serma.shopbucket.data.remote.contract.ShoppingRemoteSource
import javax.inject.Inject

class GetShoppingUseCase @Inject constructor(private val shoppingRemoteSource: ShoppingRemoteSource) {
    operator fun invoke(idPurchase: String, idShopping: String) =
        shoppingRemoteSource.getShopping(idPurchase, idShopping)
}