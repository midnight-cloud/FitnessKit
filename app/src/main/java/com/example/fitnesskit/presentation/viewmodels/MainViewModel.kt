package com.example.fitnesskit.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesskit.data.network.NetworkApi
import com.example.fitnesskit.domain.models.MainModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val api: NetworkApi) : ViewModel() {

    private val _data = MutableStateFlow(listOf<MainModel>())
    val data: StateFlow<List<MainModel>> = _data.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val result = api.getTrainingData(2)
            _data.value = listOf(result.body()!!)
        }
    }
}