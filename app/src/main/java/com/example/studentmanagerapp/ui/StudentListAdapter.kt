package com.example.studentmanagerapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.example.studentmanagerapp.R
import com.example.studentmanagerapp.data.Student

class StudentListAdapter(
    private val students: List<Student>,
    private val onItemAction: (Student, String) -> Unit
) : RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.student_name)
        val idTextView: TextView = view.findViewById(R.id.student_id)
        val menuButton: ImageButton = view.findViewById(R.id.btn_menu)

        fun bind(student: Student) {
            nameTextView.text = student.fullName
            idTextView.text = student.studentId

            menuButton.setOnClickListener {
                showPopupMenu(it, student)
            }
        }

        private fun showPopupMenu(anchor: View, student: Student) {
            val popup = PopupMenu(anchor.context, anchor)
            popup.menuInflater.inflate(R.menu.context_menu_student, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_update -> {
                        onItemAction(student, "update")
                        true
                    }
                    R.id.menu_delete -> {
                        onItemAction(student, "delete")
                        true
                    }
                    R.id.menu_sms -> {
                        onItemAction(student, "sms")
                        true
                    }
                    R.id.menu_call -> {
                        onItemAction(student, "call")
                        true
                    }
                    R.id.menu_email -> {
                        onItemAction(student, "email")
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    override fun getItemCount() = students.size
}