package com.example.studentmanagerapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Student(
    val id: String = UUID.randomUUID().toString(),
    val fullName: String,
    val studentId: String,
    val email: String,
    val phone: String
) : Parcelable