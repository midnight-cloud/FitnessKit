package com.example.fitnesskit.data.network

import com.example.fitnesskit.domain.TrainingRepo
import com.example.fitnesskit.domain.models.*
import retrofit2.Response
import javax.inject.Inject

class TrainingRepoImpl @Inject constructor(private val api: NetworkApi) : TrainingRepo {
    private var responce: MainModel? = null

    override suspend fun getTrainingData(clubId: Int): Response<MainModel> {
        responce = api.getTrainingData(clubId).body()
        return api.getTrainingData(clubId)
    }

    override fun getLessonList(): List<Lesson> {
        return responce?.lessons ?: listOf()
    }

    override fun getOption(): Option {
        return responce?.option!!
    }

    override fun getTabList(): List<Tab> {
        return responce?.tabs ?: listOf()
    }

    override fun getTrainerList(): List<Trainer> {
        return responce?.trainers ?: listOf()
    }
}