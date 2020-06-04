package com.example.tp_boukadir_v2.Adapter

import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_boukadir_v2.R
import com.example.tp_boukadir_v2.ToDo.ListeToDo

class ListAdapter (private val actionListener: ActionListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val dataSet : MutableList<ListeToDo> = mutableListOf()

    fun setData(newDataSet : List<ListeToDo>?) {
        dataSet.clear()
        if (newDataSet != null) {
            dataSet.addAll(newDataSet)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val ListView = inflater.inflate(R.layout.list,parent,false)

        Log.d("ListAdapter", "onCreateViewHolder")
        return ListViewHolder(ListView)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var listeToDo : ListeToDo = dataSet[position]
        (holder as ListViewHolder).bind(listeToDo)
    }

    inner class ListViewHolder (listView : View) : RecyclerView.ViewHolder(listView){
        private  val title : TextView = listView.findViewById(R.id.List_title)

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION){
                    val liste = dataSet[pos]
                    actionListener.onItemClicked(liste)
                }
            }
        }

        fun bind (listeToDo: ListeToDo){
            title.text = listeToDo.titre

        }
    }

    interface ActionListener {
        fun onItemClicked(listeToDo: ListeToDo)
    }


}