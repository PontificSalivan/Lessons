package com.serma.shopbucket.presentation.purchase

import com.serma.shopbucket.domain.entity.Purchase
import com.serma.shopbucket.domain.usecase.purchase.DeletePurchaseUseCase
import com.serma.shopbucket.domain.usecase.purchase.GetPurchaseUseCase
import com.serma.shopbucket.domain.usecase.purchase.SavePurchaseUseCase
import com.serma.shopbucket.domain.usecase.purchase.UpdatePurchaseUseCase
import com.serma.shopbucket.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class PurchaseViewModel @Inject constructor(
    private val deletePurchaseUseCase: DeletePurchaseUseCase,
    private val getPurchaseUseCase: GetPurchaseUseCase,
    private val updatePurchaseUseCase: UpdatePurchaseUseCase,
    private val savePurchaseUseCase: SavePurchaseUseCase
) :
    BaseViewModel<Purchase>() {

    fun deletePurchase(id: String) {
        val disposable = deletePurchaseUseCase(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                _delete.value = result
            }
        compositeDisposable.add(disposable)
    }

    fun getPurchase(id: String) {
        val disposable = getPurchaseUseCase(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                _response.value = result
            }
        compositeDisposable.add(disposable)
    }

    fun updatePurchase(id: String, name: String) {
        val disposable = updatePurchaseUseCase(id,name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                _update.value = result
            }
        compositeDisposable.add(disposable)
    }

    fun savePurchase(name: String) {
        val disposable = savePurchaseUseCase(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                _save.value = result
            }
        compositeDisposable.add(disposable)
    }

}