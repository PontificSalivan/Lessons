package com.serma.shopbucket.data.remote.contract

import com.serma.shopbucket.data.Response
import com.serma.shopbucket.domain.entity.Purchase
import io.reactivex.rxjava3.core.Single

typealias NoAction = Unit

interface PurchaseRemoteSource {
    fun deletePurchase(id: String): Single<Response<NoAction>>
    fun updatePurchase(id: String,name: String): Single<Response<NoAction>>
    fun savePurchase(name: String): Single<Response<NoAction>>
    fun getPurchase(id: String): Single<Response<Purchase>>
}