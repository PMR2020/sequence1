package com.tppmr.sequenceoneedm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tppmr.sequenceoneedm.R
import com.tppmr.sequenceoneedm.classe.ListeToDo

class ListeToDoAdapter(private val actionListener: ActionListener) :
    RecyclerView.Adapter<ListeToDoAdapter.ListeToDoViewHolder>() {

    private val dataSet: MutableList<ListeToDo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeToDoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listeToDoView = inflater.inflate(R.layout.liste_to_do, parent, false)
        return ListeToDoViewHolder(listeToDoView)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ListeToDoViewHolder, position: Int) {
        val nameOfListeToDo = dataSet[position].nomListeToDo
        holder.bind(nameOfListeToDo)
    }

    fun showData(newDataSet: MutableList<ListeToDo>) {
        dataSet.clear()
        dataSet.addAll(newDataSet)
        notifyDataSetChanged()
    }

    inner class ListeToDoViewHolder(listeToDoView: View) : RecyclerView.ViewHolder(listeToDoView) {
        private val textView: TextView = listeToDoView.findViewById(R.id.textView)
        fun bind(nameOfListeToDo: String) {
            textView.text = nameOfListeToDo
        }

        init {
            listeToDoView.setOnClickListener {
                val positionListeToDo = adapterPosition
                if (positionListeToDo != RecyclerView.NO_POSITION) {
                    val listeToDo = dataSet[positionListeToDo]
                    actionListener.onListeToDoClicked(listeToDo, positionListeToDo)
                }
            }

            listeToDoView.setOnLongClickListener() {
                val todoListPosition = adapterPosition
                if (todoListPosition != RecyclerView.NO_POSITION) {
                    actionListener.onListeToDoLongClicked(todoListPosition)
                }
                return@setOnLongClickListener true
            }
        }

    }

    interface ActionListener {
        fun onListeToDoClicked(listeToDo: ListeToDo, positionListeToDo: Int)
        fun onListeToDoLongClicked(positionListeToDo: Int)
    }
}

