package com.serma.shopbucket.presentation.purchase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.serma.shopbucket.data.AuthState
import com.serma.shopbucket.data.Cache
import com.serma.shopbucket.domain.entity.Purchase
import com.serma.shopbucket.presentation.base.BaseViewModel
import javax.inject.Inject

class PurchaseViewModel @Inject constructor(private val cache: Cache) : BaseViewModel() {

    private val _data: MutableLiveData<List<Purchase>> =
        MutableLiveData()
    val data: LiveData<List<Purchase>> = _data

    fun getData() {
        _data.value = cache.getPurchase()
    }

    fun addPurchase(purchase: Purchase){
        cache.addPurchase(purchase)
        getData()
    }

    fun deletePurchase(pos: Int){
        cache.deletePurchase(pos)
        getData()
    }
}