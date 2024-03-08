package com.example.productcatalog

import android.app.Application
import com.example.productcatalog.di.AppComponent
import com.example.productcatalog.di.DaggerAppComponent

class App : Application() {
    val component: AppComponent by lazy { DaggerAppComponent.create() }
}