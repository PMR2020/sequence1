package com.tppmr.sequenceoneedm.classe

class ProfilListeToDo(val nomPseudo: String, val profilListeToDo: MutableList<ListeToDo> )

class ListeToDo(var nomListeToDo: String, val listeToDo: MutableList<ItemToDo>)

class ItemToDo(var thingToDo: String, var isDone : Boolean = false)
