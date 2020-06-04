package com.example.tp_boukadir_v2.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_boukadir_v2.R
import com.example.tp_boukadir_v2.ToDo.ItemToDo


class ItemAdapter (private val actionListener: ActionListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val dataSet: MutableList<ItemToDo> = mutableListOf()

    fun setData(newDataSet : List<ItemToDo>?) {
        dataSet.clear()
        if (newDataSet != null) {
            dataSet.addAll(newDataSet)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val ListView = inflater.inflate(R.layout.list, parent, false)

        Log.d("ListAdapter", "onCreateViewHolder")
        return ListViewHolder(ListView)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemToDo: ItemToDo = dataSet[position]
        (holder as ListViewHolder).bind(itemToDo)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.item_title)
        private val fait: CheckBox = itemView.findViewById(R.id.cb_item)

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val liste = dataSet[pos]
                    val fait = dataSet[pos].fait
                    actionListener.onItemClicked(liste,fait)
                }
            }
        }

        fun bind(itemToDo: ItemToDo) {
            title.text = itemToDo.description
            fait.isChecked = itemToDo.fait

        }
    }

    interface ActionListener {
        fun onItemClicked(listeToDo: ItemToDo, fait : Boolean)
    }
}