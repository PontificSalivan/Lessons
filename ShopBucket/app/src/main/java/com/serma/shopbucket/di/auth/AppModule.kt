package com.serma.shopbucket.di.auth

import com.serma.shopbucket.data.Cache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {
    companion object {
        @Singleton
        @Provides
        fun provideCache() = Cache()
    }

}