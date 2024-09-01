package com.example.lokalassignment.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.lokalassignment.db.BookmarkedJobDao
import com.example.lokalassignment.model.BookmarkedJob
import com.example.lokalassignment.paging.JobPagingSource
import com.example.lokalassignment.retrofit.JobApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JobRepository(val jobApi: JobApi, val dao: BookmarkedJobDao) {

    fun getJobs() = Pager(
        config = PagingConfig(pageSize = 11, maxSize = 50),
        pagingSourceFactory = { JobPagingSource(jobApi) }
    ).liveData

    fun getBookmarkedJobs(): LiveData<List<BookmarkedJob>> {
        return dao.getAllBookmarkedJobs()
    }

    suspend fun insertBookmarkedJob(job: BookmarkedJob) {
        dao.insertJob(job)
    }

    suspend fun deleteBookmarkedJob(jobId: Int) {
        dao.deleteJobById(jobId)
    }
}