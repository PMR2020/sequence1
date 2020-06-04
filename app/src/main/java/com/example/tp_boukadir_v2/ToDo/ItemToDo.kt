package com.example.tp_boukadir_v2.ToDo

import java.io.Serializable

class ItemToDo (var description : String, var fait : Boolean) : Serializable{

    override fun toString(): String {
        return "ItemToDo(description='$description', fait=$fait)"
    }


}