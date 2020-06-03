package com.example.listedroulante.utility

import com.example.listedroulante.utility.TaskList

open class User(var userName : String = "Unnamed", var listOfList : MutableList<TaskList> = mutableListOf()){
    fun addList (list : TaskList) {
        this.listOfList.add(list)
    }

    fun removeList (index : Int) {
        this.listOfList.removeAt(index)
    }
}