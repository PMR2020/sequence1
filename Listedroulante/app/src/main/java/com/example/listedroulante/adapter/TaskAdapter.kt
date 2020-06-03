package com.example.listedroulante.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listedroulante.R
import com.example.listedroulante.utility.TaskList


class TaskAdapter(val listTask : TaskList, val clickListener : (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() : Int {
        return listTask.getSize()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.task_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listName = listTask.getElement(position).taskName
        (holder as ViewHolder).bind(listName, clickListener)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind (title : String, clickListener : (String) -> Unit) {
            val nameTask : TextView = itemView.findViewById(R.id.taskItemName)
            nameTask.text = title
            itemView.setOnClickListener {clickListener (title)}
        }
    }
}