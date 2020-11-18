package com.serma.shopbucket.domain.entity

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val id: String?,
    val nickname: String,
    val friendsId: List<String>?,
    val profilePicture: String?
)