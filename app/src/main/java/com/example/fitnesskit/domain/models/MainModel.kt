package com.example.fitnesskit.domain.models


data class MainModel(
    val trainers: List<Trainer>,
    val tabs: List<Tab>,
    val lessons: List<Lesson>,
    val option: Option,
)
