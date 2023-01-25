package com.example.fitnesskit.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.fitnesskit.domain.models.TrainingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {

    private val _data = MutableStateFlow(listOf<TrainingModel>())
    val data: StateFlow<List<TrainingModel>> = _data.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        setData()
        _data.value.sortedBy {
            it.date
        }
        _data.value.map {
            it.date = mapDate(it.date)
            it.duration = getTimeDifference(it.timeStart, it.timeEnd)
        }
    }

    private fun mapDate(date: String): String {
        val dateObject = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(date)!!
        val formatter = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
        return formatter.format(dateObject)
    }

    private fun getTimeDifference(t1: String, t2: String): String {
        val dateFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
        val time1 = dateFormat.parse(t1)
        val time2 = dateFormat.parse(t2)
        val diff = (time2.time - time1.time) / 1000
        return ("${diff / 3600}ч. ${(diff - (3600 * (diff / 3600))) / 60}мин.")
    }

    private fun setData() {
        _data.value = listOf<TrainingModel>(
            TrainingModel(
                UUID.randomUUID(),
                "26.01.2023",
                "10:00",
                "11:30",
                "Йога",
                "Мишенин Даниил",
                2,
                "Большой зал",
                null
            ),
            TrainingModel(
                UUID.randomUUID(),
                ("26.01.2023"),
                "08:00",
                "09:00",
                "Пилатес",
                "Мишенин Даниил",
                7,
                "Большой зал",
                null
            ),
            TrainingModel(
                UUID.randomUUID(),
                ("27.01.2023"),
                "14:15",
                "15:30",
                "Персональная тренировка",
                "Мишенин Даниил",
                1,
                "Тренажерный зал",
                null
            ),
            TrainingModel(
                UUID.randomUUID(),
                ("25.01.2023"),
                "10:00",
                "11:10",
                "Йога",
                "Мишенин Даниил",
                3,
                "Большой зал",
                null
            ),
            TrainingModel(
                UUID.randomUUID(),
                ("21.01.2023"),
                "17:00",
                "18:15",
                "Персональная тренировка",
                "Мишенин Даниил",
                1,
                "Тренажерный зал",
                null
            ),
            TrainingModel(
                UUID.randomUUID(),
                ("22.01.2023"),
                "10:45",
                "11:45",
                "Персональная тренировка",
                "Мишенин Даниил",
                1,
                "Тренажерный зал",
                null
            ),
            TrainingModel(
                UUID.randomUUID(),
                ("25.01.2023"),
                "09:00",
                "09:45",
                "Пилатес",
                "Мишенин Даниил",
                5,
                "Большой зал",
                null
            ),
            TrainingModel(
                UUID.randomUUID(),
                ("21.01.2023"),
                "10:30",
                "11:30",
                "Йога",
                "Мишенин Даниил",
                4,
                "Большой зал",
                null
            ),

            )
    }
}