package com.example.fitnesskit.data.di

import com.example.fitnesskit.data.network.NetworkApi
import com.example.fitnesskit.data.network.RetrofitHelper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit =
        RetrofitHelper.getInstance(gson)

    @Provides
    @Singleton
    fun provideRetrofitServices(retrofit: Retrofit) : NetworkApi =
        retrofit.create(NetworkApi::class.java)
}