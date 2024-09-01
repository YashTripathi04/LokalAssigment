package com.example.lokalassignment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val id: Int,
    val title: String,
    val primary_details: PrimaryDetails,
    val custom_link: String? = null,
    val openings_count: Int? = 1,
    val num_applications: Int? = 0,
    val views: Int? = 0,
    val company_name: String,
    val contact_preference: ContactPreference,
    val job_role: String,
    val other_details: String,
    var is_bookmarked: Boolean,
    val type: Int,
    val whatsapp_no: String? = "0000000000"
) : Parcelable