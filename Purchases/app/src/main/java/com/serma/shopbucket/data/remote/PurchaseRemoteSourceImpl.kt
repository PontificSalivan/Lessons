package com.serma.shopbucket.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.serma.shopbucket.data.Failure
import com.serma.shopbucket.data.Response
import com.serma.shopbucket.data.Success
import com.serma.shopbucket.data.remote.contract.AuthState
import com.serma.shopbucket.data.remote.contract.NoAction
import com.serma.shopbucket.data.remote.contract.PurchaseRemoteSource
import com.serma.shopbucket.domain.entity.Purchase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PurchaseRemoteSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : PurchaseRemoteSource {

    override fun deletePurchase(id: String): Single<Response<NoAction>> {
        return Single.create { emitter ->
            firebaseFirestore.collection("purchase")
                .document(id)
                .delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(Success(NoAction))
                    } else {
                        emitter.onSuccess(Failure(task.exception!!))
                    }
                }
        }
    }

    override fun updatePurchase(id: String, name: String): Single<Response<NoAction>> {
        return Single.create { emitter ->
            firebaseFirestore.collection("purchase")
                .document(id)
                .update(
                    mapOf(
                        "name" to name
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

    override fun savePurchase(name: String): Single<Response<NoAction>> {
        return Single.create { emitter ->
            val purchase = Purchase(name = name, user_uid = firebaseAuth.uid!!)
            firebaseFirestore.collection("purchase")
                .add(purchase)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(Success(NoAction))
                    } else {
                        emitter.onSuccess(Failure(task.exception!!))
                    }
                }
        }
    }

    override fun getPurchase(id: String): Single<Response<Purchase>> {
        return Single.create { emitter ->
            firebaseFirestore.collection("purchase")
                .document(id)
                .get()
                .addOnCompleteListener { task ->
                    val dto = task.result?.toObject<Purchase>()
                    if (task.isSuccessful && dto != null) {
                        emitter.onSuccess(Success(dto))
                    } else {
                        emitter.onSuccess(Failure(task.exception!!))
                    }
                }
        }
    }
}