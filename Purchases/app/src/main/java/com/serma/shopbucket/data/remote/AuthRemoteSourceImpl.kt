package com.serma.shopbucket.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.serma.shopbucket.data.remote.contract.AuthRemoteSource
import com.serma.shopbucket.data.remote.contract.AuthState
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AuthRemoteSourceImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRemoteSource {
    override fun registration(email: String, password: String): Single<AuthState> {
        return Single.create { emitter ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(AuthState.SUCCESS)
                    } else {
                        emitter.onSuccess(AuthState.FAILURE)
                    }
                }
        }
    }

    override fun login(email: String, password: String): Single<AuthState> {
        return Single.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(AuthState.SUCCESS)
                    } else {
                        emitter.onSuccess(AuthState.FAILURE)
                    }
                }
        }
    }
}