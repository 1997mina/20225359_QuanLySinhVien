package com.example.studentmanagerapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.studentmanagerapp.data.Student

class StudentDatabase(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "StudentManager.db"
        private const val TABLE_STUDENTS = "students"

        private const val KEY_ID = "id"
        private const val KEY_FULL_NAME = "full_name"
        private const val KEY_STUDENT_ID = "student_id"
        private const val KEY_EMAIL = "email"
        private const val KEY_PHONE = "phone"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_STUDENTS_TABLE = """
            CREATE TABLE $TABLE_STUDENTS (
                $KEY_ID TEXT PRIMARY KEY,
                $KEY_FULL_NAME TEXT,
                $KEY_STUDENT_ID TEXT,
                $KEY_EMAIL TEXT,
                $KEY_PHONE TEXT
            )
        """.trimIndent()
        db.execSQL(CREATE_STUDENTS_TABLE)

        db.execSQL("CREATE INDEX idx_student_name ON $TABLE_STUDENTS($KEY_FULL_NAME)")
        db.execSQL("CREATE INDEX idx_student_id ON $TABLE_STUDENTS($KEY_STUDENT_ID)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENTS")
        onCreate(db)
    }

    fun addStudent(student: Student): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_ID, student.id)
            put(KEY_FULL_NAME, student.fullName)
            put(KEY_STUDENT_ID, student.studentId)
            put(KEY_EMAIL, student.email)
            put(KEY_PHONE, student.phone)
        }
        val result = db.insert(TABLE_STUDENTS, null, values)
        db.close()
        return result
    }

    fun getAllStudents(): List<Student> {
        val students = mutableListOf<Student>()
        val selectQuery = "SELECT * FROM $TABLE_STUDENTS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                students.add(Student(
                    id = cursor.getString(0),
                    fullName = cursor.getString(1),
                    studentId = cursor.getString(2),
                    email = cursor.getString(3),
                    phone = cursor.getString(4)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return students
    }

    fun updateStudent(student: Student): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_FULL_NAME, student.fullName)
            put(KEY_STUDENT_ID, student.studentId)
            put(KEY_EMAIL, student.email)
            put(KEY_PHONE, student.phone)
        }
        val result = db.update(
            TABLE_STUDENTS,
            values,
            "$KEY_ID = ?",
            arrayOf(student.id)
        )
        db.close()
        return result
    }

    fun deleteStudent(student: Student): Int {
        val db = this.writableDatabase
        val result = db.delete(
            TABLE_STUDENTS,
            "$KEY_ID = ?",
            arrayOf(student.id)
        )
        db.close()
        return result
    }

    fun searchStudents(query: String): List<Student> {
        val students = mutableListOf<Student>()
        val db = this.readableDatabase

        val selectQuery = """
            SELECT * FROM $TABLE_STUDENTS 
            WHERE $KEY_FULL_NAME LIKE ? COLLATE NOCASE
            OR $KEY_STUDENT_ID LIKE ? COLLATE NOCASE
            OR $KEY_EMAIL LIKE ? COLLATE NOCASE
            OR $KEY_PHONE LIKE ?
        """.trimIndent()

        val searchQuery = "%$query%"
        val cursor = db.rawQuery(selectQuery,
            arrayOf(searchQuery, searchQuery, searchQuery, searchQuery))

        if (cursor.moveToFirst()) {
            do {
                students.add(Student(
                    id = cursor.getString(0),
                    fullName = cursor.getString(1),
                    studentId = cursor.getString(2),
                    email = cursor.getString(3),
                    phone = cursor.getString(4)
                ))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return students
    }
}