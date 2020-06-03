package com.example.tp1_todolist.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1_todolist.R
import com.example.tp1_todolist.model.ItemTask

class ItemAdapterTask(private val dataSetTask:MutableList<ItemTask>, private val actionlistener:ActionListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return dataSetTask.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.i("ItemViewHolderTask", "createViewHolder")
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_task, parent, false)
        return ItemViewHolderTask(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.i("ItemViewHolderTask", "onBindViewHolder")
        val itemTask = dataSetTask[position]
        when (holder) {
            is ItemViewHolderTask -> {
                holder.bind(itemTask)
            }
        }
    }

    inner class ItemViewHolderTask(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBoxTask: CheckBox = itemView.findViewById(R.id.checkBoxTask)

        init {
            checkBoxTask.setOnClickListener() {
                val taskPosition = adapterPosition
                if (taskPosition != RecyclerView.NO_POSITION) {
                    val taskDescription: String = checkBoxTask.text as String
                    val taskClickd: ItemTask = dataSetTask[taskPosition]
                    checkBoxTask.isChecked = !taskClickd.fait
                    actionlistener.onItemClicked(taskPosition)
                    Log.i("ItemViewHolderTask", "click at $taskDescription")
                }
            }

        }

        fun bind(itemTask: ItemTask) {
            checkBoxTask.text = itemTask.description
            checkBoxTask.setChecked(itemTask.fait)
        }
    }

    interface ActionListener {
        fun onItemClicked(taskPosition: Int)
    }
}


