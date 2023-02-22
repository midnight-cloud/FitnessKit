package com.example.fitnesskit.presentation.di

import android.app.Application
import android.content.Context
import com.example.fitnesskit.presentation.adapters.TrainingAdapter
import com.example.fitnesskit.presentation.viewmodels.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    fun provideTrainingAdapter(context: Context, viewModel: MainViewModel): TrainingAdapter {
        return TrainingAdapter(context, viewModel)
    }
}