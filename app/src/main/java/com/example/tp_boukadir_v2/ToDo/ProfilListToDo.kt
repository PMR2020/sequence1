package com.example.tp_boukadir_v2.ToDo

import java.io.Serializable

class ProfilListToDo (var login : String = "default", var mesListeToDo: MutableList<ListeToDo> = mutableListOf()) : Serializable{

    fun ajouterListe(listeToDo: ListeToDo){
        mesListeToDo.add(listeToDo)
    }

    fun rechercherListe (descriptionItem : String): Int? {
        for (item in this.mesListeToDo){
            if (item.titre == descriptionItem) return mesListeToDo.indexOf(item)
        }
        return null
    }

    override fun toString(): String {
        return "ProfilListToDo(login='$login', mesListeToDo=${mesListeToDo.size})"
    }


}