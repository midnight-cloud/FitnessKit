package com.example.fitnesskit.presentation.di

import android.app.Application
import android.content.Context
import com.example.fitnesskit.data.di.NetworkModule
import com.example.fitnesskit.presentation.MainActivity
import com.example.fitnesskit.presentation.fragments.MainFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (NetworkModule::class)])
@Singleton
interface AppComponent {
    fun context(): Context
    fun applicationContext() : Application
    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)
}