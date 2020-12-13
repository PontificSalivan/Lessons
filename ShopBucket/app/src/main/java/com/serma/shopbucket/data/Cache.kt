package com.serma.shopbucket.data

import com.serma.shopbucket.domain.entity.Purchase
import com.serma.shopbucket.domain.entity.Shopping
import javax.inject.Inject

class Cache @Inject constructor() {

    private val purchaseList: MutableList<Purchase> = mutableListOf()

    fun addPurchase(purchase: Purchase) {
        purchaseList.add(purchase)
    }

    fun addShopping(posPurchase: Int, shopping: Shopping) {
        purchaseList[posPurchase].shoppingList.add(shopping)
    }

    fun changeShopping(posPurchase: Int, posShopping: Int, shopping: Shopping) {
        purchaseList[posPurchase].shoppingList[posShopping] = shopping
    }

    fun deleteShopping(posPurchase: Int, posShopping: Int) {
        purchaseList[posPurchase].shoppingList.removeAt(posShopping)
    }

    fun changePurchase(posPurchase: Int, purchase: Purchase) {
        purchaseList[posPurchase] = purchase
    }

    fun deletePurchase(posPurchase: Int) {
        purchaseList.removeAt(posPurchase)
    }

    fun getPurchase(pos: Int) = purchaseList[pos]

    fun getPurchase() = purchaseList

    fun getShopping(posPurchase: Int) = purchaseList[posPurchase].shoppingList

    fun getPurchasePos() = purchaseList.size
}