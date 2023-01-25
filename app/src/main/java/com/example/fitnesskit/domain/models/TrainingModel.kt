package com.example.fitnesskit.domain.models

import java.util.*


data class TrainingModel(
    val id: UUID,
    var date: String,
    val timeStart: String,
    val timeEnd: String,
    val title: String,
    val trainer: String,
    val countVisitors: Int,
    val place: String,
    var duration: String?
)
