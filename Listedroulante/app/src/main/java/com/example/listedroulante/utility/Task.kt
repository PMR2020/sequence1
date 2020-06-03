package com.example.listedroulante.utility

class Task(var taskName: String = "Unnamed", var description: String = "Unmentioned", val id : Int) {

    fun devTask (dev : String) {
        this.description += " $dev"
    }
}