package com.example.fitnesskit.presentation

import android.app.Application
import android.content.Context
import com.example.fitnesskit.presentation.di.AppComponent
import com.example.fitnesskit.presentation.di.AppModule
import com.example.fitnesskit.presentation.di.DaggerAppComponent

class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}

val Context.appComponent: AppComponent
get() = when(this) {
    is App -> appComponent
    else -> this.applicationContext.appComponent
}