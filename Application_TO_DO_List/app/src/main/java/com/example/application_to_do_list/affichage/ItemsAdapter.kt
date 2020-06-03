package com.example.application_to_do_list.affichage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.application_to_do_list.R
import com.example.application_to_do_list.modele.Item
import com.example.application_to_do_list.modele.Liste
import kotlinx.android.synthetic.main.element_item.view.*

class ItemsAdapter(val liste: Liste, private var actionListener: ItemsAdapter.ActionListener) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init{
                view.checkItem.setOnClickListener{
                    val position = adapterPosition
                    if(position!=RecyclerView.NO_POSITION) {
                        val itemClic : Item = liste.items[position]
                        actionListener.onItemClicked(itemClic)

                    }
                }
        } //Valeur du checkItem
        val nomItem = view.checkItem
    }

    override fun getItemCount(): Int = liste.items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val lineView = LayoutInflater.from(parent.context).inflate(R.layout.element_item, parent, false)
        return ViewHolder(lineView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.nomItem?.text= liste.items[position].title
        holder?.nomItem?.isChecked = liste.items[position].checked
    }

    interface ActionListener{
        fun onItemClicked(item: Item ){
        }
    }
}