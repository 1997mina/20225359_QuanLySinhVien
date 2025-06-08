package com.example.studentmanagerapp.viewmodel

import android.app.Application
import kotlinx.coroutines.launch

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope

import com.example.studentmanagerapp.database.Student
import com.example.studentmanagerapp.database.StudentDatabase
import com.example.studentmanagerapp.repository.StudentRepository

class StudentViewModel(application: Application) : AndroidViewModel(application) {
    private val studentRepository: StudentRepository
    val allStudents: LiveData<List<Student>>
    val searchResults = MutableLiveData<List<Student>>()

    init {
        val dao = StudentDatabase.getDatabase(application).studentDao()
        studentRepository = StudentRepository(dao)
        allStudents = studentRepository.allStudents.asLiveData()
    }

    fun insert(student: Student) = viewModelScope.launch {
        studentRepository.insert(student)
    }

    fun update(student: Student) = viewModelScope.launch {
        studentRepository.update(student)
    }

    fun delete(student: Student) = viewModelScope.launch {
        studentRepository.delete(student)
    }

    fun search(query: String) = viewModelScope.launch {
        studentRepository.search(query).collect { results ->
            searchResults.postValue(results)
        }
    }
}
