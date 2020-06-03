package com.example.application_to_do_list.modele

import android.util.Log
import com.example.application_to_do_list.data.Prefs
import com.google.gson.Gson
import java.nio.file.attribute.UserDefinedFileAttributeView

class Utilisateur (val pseudo : String="",var listes: MutableList<Liste> = emptyList<Liste>().toMutableList()){
    val id : Int

    init{
        this.id=utilisateurs.size
        utilisateurs.add(this)
    }

    fun ajoutListe(liste : Liste) {
        listes.add(liste)
    }

    fun getListe(title : String) : Liste{
        for (liste in listes){
            if(liste.title==title){
                return liste
            }
        }
        return Liste(title)
    }

    override fun toString(): String {
        var listesString = """{"pseudo": "$pseudo","listes": ["""
        for (l in listes){
            listesString+=l.toString()+","
        }
        if(listesString[listesString.length-1]==','){
            listesString=listesString.substring(0, listesString.length-1)
        }
        return listesString+"]}"
    }

    companion object {
        var utilisateurs =emptyList<Utilisateur>().toMutableList()


        fun getUser(pseudo : String ) : Utilisateur {
            for (u in utilisateurs) {
                if(u.pseudo==pseudo) return u
            }
            return Utilisateur(pseudo)
        }


        fun usersToString(): String{
            var usersString = "["
            for (u in utilisateurs){
                usersString+=(u.toString())+","
            }
            return usersString +"]"
        }


    }


}