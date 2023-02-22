package com.example.fitnesskit.domain

import com.example.fitnesskit.domain.models.*
import retrofit2.Response

interface TrainingRepo {
    suspend fun getTrainingData(clubId: Int): Response<MainModel>
    fun getLessonList(): List<Lesson>
    fun getOption(): Option
    fun getTabList(): List<Tab>
    fun getTrainerList(): List<Trainer>
}