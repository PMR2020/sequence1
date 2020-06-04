package com.example.tp_boukadir_v2.ToDo

import java.io.Serializable

class ListeToDo ( var titre : String, var LesItem : MutableList<ItemToDo> = mutableListOf()) : Serializable{



    fun rechercherItem (descriptionItem : String): Int? {
        for (item in this.LesItem){
            if (item.description==descriptionItem) return LesItem.indexOf(item)
        }
        return null
    }

    fun ajouterItem(itemToDo: ItemToDo){
        LesItem.add(itemToDo)
    }

    fun updateItem(itemToDo: ItemToDo) {
        val id = rechercherItem(itemToDo.description)
        if (id != null) {
            LesItem[id] = itemToDo
        }
        else {
            ajouterItem(itemToDo)
        }
    }

    override fun toString(): String {
        return "ListeToDo(titre='$titre', LesItem=$LesItem)"
    }
}


