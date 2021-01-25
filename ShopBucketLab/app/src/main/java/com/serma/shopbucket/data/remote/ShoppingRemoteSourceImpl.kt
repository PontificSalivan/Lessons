package com.serma.shopbucket.data.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.serma.shopbucket.data.Failure
import com.serma.shopbucket.data.Response
import com.serma.shopbucket.data.Success
import com.serma.shopbucket.data.remote.contract.NoAction
import com.serma.shopbucket.data.remote.contract.ShoppingRemoteSource
import com.serma.shopbucket.domain.entity.Shopping
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

class ShoppingRemoteSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ShoppingRemoteSource {

    override fun deleteShopping(
        idPurchase: String,
        idShopping: String,
        price: Long,
        count: Long,
        complete: Boolean
    ): Single<Response<NoAction>> {
        val ref = firebaseFirestore.collection("purchase").document(idPurchase)
        return Single.zip(
            Single.just(
                ref.update("total", FieldValue.increment(-1L))
            ).subscribeOn(Schedulers.io()),
            Single.just(
                ref.update("totalPrice", FieldValue.increment(-1 * count * price))
            ).subscribeOn(Schedulers.io()),
            Single.just(
                if (complete)
                    ref.update("progress", FieldValue.increment(-1L)).isSuccessful
                else true
            ).subscribeOn(Schedulers.io())
        ) { first, second, _ ->
            Single.create { emitter: SingleEmitter<Response<NoAction>> ->
                ref.collection("shopping")
                    .document(idShopping)
                    .delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful && first.isSuccessful && second.isSuccessful) {
                            emitter.onSuccess(Success(NoAction))
                        } else {
                            emitter.onSuccess(Failure(task.exception ?: Exception()))
                        }
                    }
            }.blockingGet()
        }
    }

    override fun updateShopping(
        purchaseId: String,
        shoppingId: String,
        name: String,
        count: Long,
        price: Long
    ): Single<Response<NoAction>> {
        val ref = firebaseFirestore.collection("purchase").document(purchaseId)
        return Single.just(
            ref.update(
                "totalPrice", FieldValue.increment(price * count)
            )
        ).flatMap {
            Single.create { emitter ->
                ref.collection("shopping")
                    .document(shoppingId)
                    .update(
                        mapOf(
                            "name" to name,
                            "count" to count,
                            "price" to price
                        )
                    )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emitter.onSuccess(Success(NoAction))
                        } else {
                            emitter.onSuccess(Failure(task.exception!!))
                        }
                    }
            }
        }
    }

    override fun updateShopping(
        purchaseId: String,
        shoppingId: String,
        complete: Boolean
    ): Single<Response<NoAction>> {
        val ref = firebaseFirestore.collection("purchase").document(purchaseId)
        return Single.just(
            if (complete)
                ref.update(
                    "progress", FieldValue.increment(1L)
                )
            else ref.update(
                "progress",
                FieldValue.increment(-1L)
            )
        ).flatMap {
            Single.create { emitter ->
                ref.collection("shopping")
                    .document(shoppingId)
                    .update(
                        mapOf(
                            "complete" to complete
                        )
                    )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emitter.onSuccess(Success(NoAction))
                        } else {
                            emitter.onSuccess(Failure(task.exception!!))
                        }
                    }
            }
        }
    }

    override fun saveShopping(
        purchaseId: String,
        name: String,
        count: Long,
        price: Long
    ): Single<Response<NoAction>> {
        val ref = firebaseFirestore.collection("purchase").document(purchaseId)
        return Single.zip(
            Single.just(
                ref.update(
                    "totalPrice", FieldValue.increment(price * count)
                )
            ), Single.just(
                ref.update("total", FieldValue.increment(1L))
            ), ::Pair
        ).flatMap {
            Single.create { emitter ->
                val shopping =
                    Shopping(
                        name = name,
                        count = count,
                        price = price,
                        user_uid = firebaseAuth.uid!!
                    )
                ref.collection("shopping")
                    .add(shopping)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful && it.first.isSuccessful && it.second.isSuccessful) {
                            emitter.onSuccess(Success(NoAction))
                        } else {
                            emitter.onSuccess(Failure(task.exception!!))
                        }
                    }
            }
        }
    }


    override fun getShopping(
        idPurchase: String,
        idShopping: String
    ): Single<Response<Shopping>> {
        return Single.create { emitter ->
            firebaseFirestore.collection("purchase")
                .document(idPurchase)
                .collection("shopping")
                .document(idShopping)
                .get()
                .addOnCompleteListener { task ->
                    val dto = task.result?.toObject<Shopping>()
                    if (task.isSuccessful && dto != null) {
                        emitter.onSuccess(Success(dto))
                    } else {
                        emitter.onSuccess(Failure(task.exception!!))
                    }
                }
        }
    }
}