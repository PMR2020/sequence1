package com.tppmr.sequenceoneedm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tppmr.sequenceoneedm.R
import com.tppmr.sequenceoneedm.classe.ItemToDo

class ItemToDoAdapter(private val actionListener : ActionListener): RecyclerView.Adapter<ItemToDoAdapter.ItemToDoViewHolder>() {

    private val dataSet: MutableList<ItemToDo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemToDoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemToDoView = inflater.inflate(R.layout.item_to_do, parent, false)
        return ItemToDoViewHolder(itemToDoView)
    }

    override fun getItemCount(): Int = dataSet.size

    fun showData(newDataSet: MutableList<ItemToDo>){
        dataSet.clear()
        dataSet.addAll(newDataSet)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemToDoViewHolder, position: Int) {
        val nameOfItemToDo = dataSet[position].thingToDo
        val isDone = dataSet[position].isDone
        holder.bind(nameOfItemToDo,isDone)
    }

    inner class ItemToDoViewHolder(itemToDoView: View): RecyclerView.ViewHolder(itemToDoView) {
        val textView: TextView = itemToDoView.findViewById(R.id.textView)
        val checkBox: CheckBox = itemToDoView.findViewById(R.id.checkBox)
        fun bind(nameOfItemOfTodoList: String, isDone: Boolean){
            textView.text = nameOfItemOfTodoList
            checkBox.isChecked = isDone
        }

        init {
            itemToDoView.setOnLongClickListener() {
                val positionItemToDo= adapterPosition
                if (positionItemToDo != RecyclerView.NO_POSITION) {
                    actionListener.onItemToDoLongClicked(positionItemToDo)
                }
                return@setOnLongClickListener true
            }

            checkBox.setOnClickListener(){
                val positionItemToDo = adapterPosition
                if (positionItemToDo != RecyclerView.NO_POSITION) {
                    actionListener.onItemToDoCheckBoxClicked(positionItemToDo)
                }
            }
        }
    }

    interface ActionListener{
        fun onItemToDoLongClicked(positionItemToDo: Int)
        fun onItemToDoCheckBoxClicked(positionItemToDo: Int)
    }
}