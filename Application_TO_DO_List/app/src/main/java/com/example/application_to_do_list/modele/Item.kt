package com.example.application_to_do_list.modele

class Item(val title : String = "Non spécifié", var checked : Boolean= false) {
    fun chgtStatut(){
        checked = !checked
    }

    override fun toString(): String {

        return """{"titre" : "$title","checked" : "$checked"}"""
    }

}