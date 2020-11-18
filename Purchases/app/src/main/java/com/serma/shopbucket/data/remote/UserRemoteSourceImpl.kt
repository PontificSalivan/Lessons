package com.serma.shopbucket.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.serma.shopbucket.data.Failure
import com.serma.shopbucket.data.Response
import com.serma.shopbucket.data.Success
import com.serma.shopbucket.data.remote.contract.UserRemoteSource
import com.serma.shopbucket.domain.entity.User
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRemoteSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) :
    UserRemoteSource {

    companion object {
        const val USER_COLLECTION = "user"
        const val NICKNAME_FIELD = "nickname"
    }

    override fun getUser(id: String): Single<Response<User>> {
        return Single.create { emitter ->
            db.collection(USER_COLLECTION)
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document == null || !document.exists()) {
                        emitter.onError(NoSuchElementException("No such document"))
                    }
                    emitter.onSuccess(Success(document.toObject(User::class.java)!!))
                }
                .addOnFailureListener { error ->
                    emitter.onSuccess(Failure(error))
                }
        }
    }

    private fun postUser(user: User): Single<Response<AuthState>> {
        return Single.create { emitter ->
            db.collection(USER_COLLECTION)
                .add(user)
                .addOnSuccessListener {
                    emitter.onSuccess(Success(AuthState.SUCCESS))
                }
                .addOnFailureListener { error ->
                    emitter.onSuccess(Failure(error))
                }
        }
    }

    override fun login(email: String, password: String): Single<Response<AuthState>> {
        return Single.create { emitter ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(Success(AuthState.SUCCESS))
                    } else {
                        emitter.onSuccess(Failure(task.exception!!))
                    }
                }
        }
    }

    override fun registration(
        email: String,
        password: String
    ): Single<Response<AuthState>> {
        return Single.create<Response<AuthState>> { emitter ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(Success(AuthState.SUCCESS))
                    } else {
                        emitter.onSuccess(Failure(task.exception!!))
                    }
                }
        }.flatMap { registerState ->
            when (registerState) {
                is Success -> postUser(User(auth.uid, nickname, null, null))
                is Failure -> Single.just(registerState)
            }
        }
    }

}