package com.example.lokalassignment.retrofit

import com.example.lokalassignment.model.JobList
import retrofit2.http.GET
import retrofit2.http.Query

interface JobApi {
    @GET("common/jobs?")
    suspend fun getJobs(@Query("page") page: Int): JobList
}