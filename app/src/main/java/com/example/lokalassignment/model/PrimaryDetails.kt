package com.example.lokalassignment.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PrimaryDetails(
    val Experience: String,
    val Place: String,
    val Qualification: String,
    val Salary: String
//    val Fees_Charged: String?,
//    val Job_Type: String?,
) : Parcelable