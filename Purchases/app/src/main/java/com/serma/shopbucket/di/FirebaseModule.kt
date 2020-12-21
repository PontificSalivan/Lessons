package com.serma.shopbucket.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface FirebaseModule {

    companion object{
        @Provides
        @Singleton
        fun provideAuthFirebase() = FirebaseAuth.getInstance()

        @Provides
        @Singleton
        fun provideFirestore() = FirebaseFirestore.getInstance()
    }
}