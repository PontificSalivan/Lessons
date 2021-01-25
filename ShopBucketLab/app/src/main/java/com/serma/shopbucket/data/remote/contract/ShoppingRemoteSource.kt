package com.serma.shopbucket.data.remote.contract

import com.serma.shopbucket.data.Response
import com.serma.shopbucket.domain.entity.Shopping
import io.reactivex.rxjava3.core.Single

interface ShoppingRemoteSource {
    fun deleteShopping(
        idPurchase: String,
        idShopping: String,
        price: Long,
        count: Long,
        complete: Boolean
    ): Single<Response<NoAction>>

    fun updateShopping(
        purchaseId: String,
        shoppingId: String,
        name: String,
        count: Long,
        price: Long
    ): Single<Response<NoAction>>

    fun updateShopping(
        purchaseId: String,
        shoppingId: String,
        complete: Boolean
    ): Single<Response<NoAction>>

    fun saveShopping(
        purchaseId: String,
        name: String,
        count: Long,
        price: Long
    ): Single<Response<NoAction>>

    fun getShopping(idPurchase: String, idShopping: String): Single<Response<Shopping>>
}