package com.example.studentmanagerapp.data

import android.content.Context
import com.example.studentmanagerapp.database.StudentDatabase

object StudentDataSource {
    private lateinit var database: StudentDatabase

    fun initialize(context: Context) {
        database = StudentDatabase(context)
    }

    fun getStudents(): List<Student> {
        return database.getAllStudents()
    }

    fun addStudent(student: Student) {
        database.addStudent(student)
    }

    fun updateStudent(student: Student) {
        database.updateStudent(student)
    }

    fun removeStudent(student: Student) {
        database.deleteStudent(student)
    }

    fun searchStudents(query: String): List<Student> {
        return database.searchStudents(query)
    }
}