package com.serma.shopbucket.provider

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

interface FirebaseProvider {
    fun provideFirestore(): FirebaseFirestore

    fun provideFirebaseAuth(): FirebaseAuth
}