package com.serma.shopbucket.presentation.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.serma.shopbucket.data.Cache
import com.serma.shopbucket.domain.entity.Purchase
import com.serma.shopbucket.domain.entity.Shopping
import com.serma.shopbucket.presentation.base.BaseViewModel
import javax.inject.Inject

class ShoppingViewModel @Inject constructor(private val cache: Cache) : BaseViewModel() {

    private val _data: MutableLiveData<List<Shopping>> =
        MutableLiveData()
    val data: LiveData<List<Shopping>> = _data

    var shopping: Shopping? = null

    fun getData(pos: Int) {
        _data.value = cache.getShopping(pos - 1)
    }

    fun addShopping(pos: Int, shopping: Shopping) {
        cache.addShopping(pos - 1, shopping)
        getData(pos)
    }

    fun updatePurchaseWithShopping(pos: Int, shopping: Shopping) {
        val item = cache.getPurchase(pos - 1)
        val result = cache.getPurchase(pos - 1).copy(total = item.total + 1)
        cache.changePurchase(pos - 1, result)
    }

    fun addPurchase(purchase: Purchase) {
        cache.addPurchase(purchase)
    }

    fun changePurchase(purchase: Purchase) {
        cache.addPurchase(purchase)
    }

    fun deleteShopping(posPurchase: Int, posShopping: Int) {
        cache.deleteShopping(posPurchase - 1, posShopping)
        getData(posPurchase)
    }

    fun getPurchasePos() = cache.getPurchasePos()

    fun saveShopping(newShopping: Shopping) {
        shopping = newShopping
    }
}