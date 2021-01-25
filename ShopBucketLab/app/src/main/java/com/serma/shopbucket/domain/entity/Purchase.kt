package com.serma.shopbucket.domain.entity

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Purchase(
    @DocumentId override val id: String? = null,
    val name: String = "",
    val progress: Long = 0,
    val total: Long = 0,
    val user_uid: String = "",
    val totalPrice: Long = 0
) : BaseFireStoreEntity, Parcelable
