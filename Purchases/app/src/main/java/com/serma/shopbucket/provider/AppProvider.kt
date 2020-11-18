package com.serma.shopbucket.provider

import android.content.Context

interface AppProvider {
    fun provideContext(): Context
}