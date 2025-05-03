package com.example.studentmanagerapp.data

object StudentDataSource {
    private val studentList = mutableListOf<Student>()

    fun getStudents(): List<Student> {
        return studentList.toList()
    }

    fun addStudent(student: Student) {
        studentList.add(student)
    }

    fun removeStudent(student: Student) {
        studentList.remove(student)
    }

    fun updateStudent(updatedStudent: Student) {
        val index = studentList.indexOfFirst { it.id == updatedStudent.id }
        if (index != -1) {
            studentList[index] = updatedStudent
        }
    }
}