package com.serma.shopbucket.domain.usecase.shopping

import com.serma.shopbucket.data.remote.contract.ShoppingRemoteSource
import javax.inject.Inject

class DeleteShoppingUseCase @Inject constructor(
    private val shoppingRemoteSource: ShoppingRemoteSource
) {
    operator fun invoke(idPurchase: String, idShopping: String, complete: Boolean) =
        shoppingRemoteSource.deleteShopping(idPurchase, idShopping, complete)
}