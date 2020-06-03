package com.example.listedroulante.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listedroulante.R
import com.example.listedroulante.utility.TaskList

class ListAdapter(val listList : MutableList<TaskList>, val clickListener : (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() : Int {
        return listList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.list_item, parent, false)

        return ViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listName = listList[position].listName
        val listDesc = listList[position].listDescription
        (holder as ViewHolder).bind(listName, listDesc, clickListener)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) { //, View.OnClickListener {

        fun bind (title : String, titleB : String, clickListener : (String) -> Unit) {
            val nameList : TextView = itemView.findViewById(R.id.listItemName)
            val descList : TextView = itemView.findViewById(R.id.listItemDesc)
            nameList.text = title
            descList.text = titleB
            itemView.setOnClickListener {clickListener (title)}
        }
    }
}