package com.serma.shopbucket.domain.entity

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Shopping(
    @DocumentId override val id: String? = null,
    val name: String = "",
    val count: Long = 0,
    val price: Long = 0,
    val user_uid: String = "",
    val complete: Boolean = false
) : BaseFireStoreEntity, Parcelable
