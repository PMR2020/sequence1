package com.example.application_to_do_list.affichage

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.application_to_do_list.ChoixListActivity
import com.example.application_to_do_list.R
import com.example.application_to_do_list.modele.Liste
import kotlinx.android.synthetic.main.element_liste.view.*

class ListesAdapter(val items: List<Liste>, private val actionListener: ActionListener) : RecyclerView.Adapter<ListesAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init{
            view.txtEltListe.setOnClickListener{
                val position = adapterPosition
                if(position!=RecyclerView.NO_POSITION) {
                    val listeClic : Liste = items[position]
                    actionListener.onListClicked(listeClic)

                }
            }
        }
        //Valeur du TextView
        val nomListe = view.txtEltListe
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val lineView = LayoutInflater.from(parent.context).inflate(R.layout.element_liste, parent, false)
        return ViewHolder(lineView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.nomListe?.text= items[position].title
    }


    interface ActionListener{
        fun onListClicked(liste : Liste){
        }
    }
}