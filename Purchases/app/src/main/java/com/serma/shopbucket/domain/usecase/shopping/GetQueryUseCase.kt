package com.serma.shopbucket.domain.usecase.shopping

import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.serma.shopbucket.domain.entity.Shopping
import javax.inject.Inject

class GetQueryUseCase @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {
    operator fun invoke(id: String) =
        FirestoreRecyclerOptions.Builder<Shopping>()
            .setQuery(
                firebaseFirestore
                    .collection("purchase")
                    .document(id)
                    .collection("shopping"),
                Shopping::class.java
            )
            .build()
}