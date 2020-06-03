package com.example.todoapp.data

import java.io.Serializable

data class ToDoItem (
    var description : String = "",
    var done : Boolean = false
) : Serializable