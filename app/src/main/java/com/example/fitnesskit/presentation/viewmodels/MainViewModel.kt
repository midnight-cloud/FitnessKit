package com.example.fitnesskit.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesskit.data.network.TrainingRepoImpl
import com.example.fitnesskit.domain.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repo: TrainingRepoImpl) : ViewModel() {

    private val _data = MutableStateFlow(listOf<MainModel>())
    val data: StateFlow<List<MainModel>> = _data.asStateFlow()

    private val _tab = MutableStateFlow(listOf<Tab>())
    val tab: StateFlow<List<Tab>> = _tab.asStateFlow()

    private val _trainer = MutableStateFlow(listOf<Trainer>())
    val trainer: StateFlow<List<Trainer>> = _trainer.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    init {
        loadData()
        Log.d("MAIN_TAG", "viewmodel loaded")
    }

    private fun loadData() {
        _loading.value = true
        viewModelScope.launch {
            _data.value = listOf(repo.getTrainingData(2).body()!!)
            _tab.value = repo.getTabList()
            _trainer.value = repo.getTrainerList()
            _loading.value = false
        }
    }
}