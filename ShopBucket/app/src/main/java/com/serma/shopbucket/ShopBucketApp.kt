package com.serma.shopbucket

import android.app.Application
import com.serma.dionysusproject.di.AppComponent
import com.serma.dionysusproject.di.DaggerAppComponent

class ShopBucketApp : Application() {

    private var appComponent: AppComponent? = null

    fun getAppComponent(): AppComponent {
        return appComponent ?: DaggerAppComponent.factory().create(applicationContext)
            .also { appComponent = it }
    }
}