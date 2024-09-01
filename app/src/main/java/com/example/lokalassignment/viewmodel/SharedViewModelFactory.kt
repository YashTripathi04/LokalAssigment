package com.example.lokalassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lokalassignment.repository.JobRepository

class SharedViewModelFactory(private val jobRepository: JobRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SharedViewModel(jobRepository) as T
    }
}