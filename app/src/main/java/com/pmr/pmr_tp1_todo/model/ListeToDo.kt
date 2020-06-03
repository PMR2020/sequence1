package com.pmr.pmr_tp1_todo.model

class ListeToDo(var titreListeToDo : String,var active:Boolean=false) {
    var listItemsToDo : MutableList<ItemToDo> = mutableListOf()
}
