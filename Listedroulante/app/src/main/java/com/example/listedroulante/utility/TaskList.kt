package com.example.listedroulante.utility

import com.example.listedroulante.utility.Task

class TaskList (var listName : String = "Unnamed", var listDescription : String = "Unmentioned", var listOfTask : MutableList<Task> = mutableListOf()) {
    fun addTask (task : Task) {
        this.listOfTask.add(task)
    }

    fun removeTask (index : Int) {
        this.listOfTask.removeAt(index)
    }

    fun getSize (): Int {
        return this.listOfTask.size
    }

    fun getElement (index : Int): Task {
        return this.listOfTask[index]
    }
}