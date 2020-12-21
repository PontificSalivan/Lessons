package com.serma.shopbucket.presentation.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.serma.shopbucket.domain.entity.Shopping
import com.serma.shopbucket.domain.usecase.shopping.*
import com.serma.shopbucket.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ShoppingViewModel @Inject constructor(
    private val getQueryUseCase: GetQueryUseCase,
    private val deleteShoppingUseCase: DeleteShoppingUseCase,
    private val saveShoppingUseCase: SaveShoppingUseCase,
    private val updateShoppingUseCase: UpdateShoppingUseCase,
    private val getShoppingUseCase: GetShoppingUseCase
) : BaseViewModel<Shopping>() {

    private val _data: MutableLiveData<List<Shopping>> =
        MutableLiveData()
    val data: LiveData<List<Shopping>> = _data

    fun getQuery(id: String) = getQueryUseCase(id)

    fun updateShopping(
        purchaseId: String,
        shoppingId: String,
        name: String,
        count: Long,
        price: Long
    ) {
        val disposable = updateShoppingUseCase(purchaseId, shoppingId, name, count, price)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                _update.value = result
            }
        compositeDisposable.add(disposable)
    }

    fun updateShopping(
        purchaseId: String,
        shoppingId: String,
        complete: Boolean
    ) {
        val disposable = updateShoppingUseCase(purchaseId, shoppingId, complete)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                _update.value = result
            }
        compositeDisposable.add(disposable)
    }

    fun saveShopping(
        purchaseId: String,
        name: String,
        count: Long,
        price: Long
    ) {
        val disposable = saveShoppingUseCase(purchaseId, name, count, price)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                _save.value = result
            }
        compositeDisposable.add(disposable)
    }

    fun deleteShopping(idPurchase: String, idShopping: String, complete: Boolean) {
        val disposable = deleteShoppingUseCase(idPurchase, idShopping, complete)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                _delete.value = result
            }
        compositeDisposable.add(disposable)
    }

    fun getShopping(idPurchase: String, idShopping: String) {
        val disposable = getShoppingUseCase(idPurchase, idShopping)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                _response.value = result
            }
        compositeDisposable.add(disposable)
    }

}