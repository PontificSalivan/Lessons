package com.serma.shopbucket

import android.app.Application

class ShopBucketApp : Application() {

    private var appComponent: AppComponent? = null

    fun getAppComponent(): AppComponent {
        return appComponent ?: DaggerAppComponent.factory().create(applicationContext)
    }
}