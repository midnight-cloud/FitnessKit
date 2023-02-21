package com.example.fitnesskit.data.network

import com.example.fitnesskit.domain.models.MainModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("/schedule/get_v3")
//    suspend fun getTrainingData(): Response<MainModel>
    suspend fun getTrainingData(@Query("club_id") clubId: Int): Response<MainModel>
}