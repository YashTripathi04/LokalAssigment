package com.example.lokalassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.lokalassignment.model.BookmarkedJob
import com.example.lokalassignment.repository.JobRepository
import kotlinx.coroutines.launch

class SharedViewModel(private val jobRepository: JobRepository): ViewModel() {
    val jobList = jobRepository.getJobs().cachedIn(viewModelScope)

    val bookmarkedJobs: LiveData<List<BookmarkedJob>> = jobRepository.getBookmarkedJobs()

    fun bookmarkJob(job: BookmarkedJob) {
        viewModelScope.launch {
            jobRepository.insertBookmarkedJob(job)
        }
    }

    fun unBookmarkJob(jobId: Int) {
        viewModelScope.launch {
            jobRepository.deleteBookmarkedJob(jobId)
        }
    }
}