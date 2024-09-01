package com.example.lokalassignment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactPreference(
//    val preference: Int?,
//    val preferred_call_end_time: String?,
//    val preferred_call_start_time: String?,
    val whatsapp_link: String
) : Parcelable