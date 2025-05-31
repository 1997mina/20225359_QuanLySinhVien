package com.example.studentmanagerapp.application

import android.app.Application
import com.example.studentmanagerapp.data.StudentDataSource

class StudentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        StudentDataSource.initialize(this)
    }
}