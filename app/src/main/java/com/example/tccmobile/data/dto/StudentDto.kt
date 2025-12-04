package com.example.tccmobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentDto (
    @SerialName("user_id")
    val userId: String,

    @SerialName("course")
    val course: String
)