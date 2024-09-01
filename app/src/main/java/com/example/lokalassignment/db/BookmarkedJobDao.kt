package com.example.lokalassignment.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lokalassignment.model.BookmarkedJob

@Dao
interface BookmarkedJobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: BookmarkedJob)

    @Query("SELECT * FROM bookmarked_jobs")
    fun getAllBookmarkedJobs(): LiveData<List<BookmarkedJob>>

    @Query("DELETE FROM bookmarked_jobs WHERE jobId = :jobId")
    suspend fun deleteJobById(jobId: Int)
}