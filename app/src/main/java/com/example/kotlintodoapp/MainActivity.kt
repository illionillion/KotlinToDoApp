package com.example.kotlintodoapp

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private val tasks = mutableListOf<String>()
    private lateinit var adapter: TaskAdapter
    private lateinit var addTaskButton: Button
    private lateinit var taskListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addTaskButton = findViewById(R.id.addTaskButton)
        taskListView = findViewById(R.id.taskListView)

        adapter = TaskAdapter()
        taskListView.adapter = adapter

        addTaskButton.setOnClickListener {
            val taskName = "Task ${tasks.size + 1}"
            tasks.add(taskName)
            adapter.notifyDataSetChanged()
        }
    }

    private inner class TaskAdapter : BaseAdapter() {
        override fun getCount(): Int = tasks.size

        override fun getItem(position: Int): Any = tasks[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View = convertView ?: LayoutInflater.from(this@MainActivity)
                .inflate(R.layout.item_task, parent, false)

            val taskNameEditText: EditText = view.findViewById(R.id.taskNameEditText)
            val completeButton: Button = view.findViewById((R.id.completeButton))
            val deleteButton: Button = view.findViewById(R.id.deleteButton)

            val taskName = tasks[position]
            taskNameEditText.setText(taskName)

            completeButton.setOnClickListener {
                taskNameEditText.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            }

            deleteButton.setOnClickListener {
                tasks.removeAt(position)
                notifyDataSetChanged()
            }

            return view
        }
    }
}