package com.example.application_to_do_list.modele

import android.util.Log

class Liste(val title : String = "Nouvelle liste", var items: MutableList<Item> = emptyList<Item>().toMutableList()) {

    fun ajoutItem(item : Item){
        items.add(item)
    }

    override fun toString(): String {
        var listesString = """{"title": "$title","items": ["""
        for (i in items){
            listesString+=items.toString()+","
        }
        if(listesString[listesString.length-1]==','){
            listesString=listesString.substring(0, listesString.length-1)
        }
        return listesString+"]}"
    }
}