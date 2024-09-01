package com.example.lokalassignment.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "bookmarked_jobs")
data class BookmarkedJob(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jobId: Int,
    val title: String,
    val place: String,
    val salary: String,
    val phone: String,
    val openings: String,
    val qualifications: String,
    val experience: String,
    val noOfApplications: String,
    val views: String,
    val companyName: String,
    val jobDescription: String,
    val whatsapp: String,
    val jobRole: String
) : Parcelable
