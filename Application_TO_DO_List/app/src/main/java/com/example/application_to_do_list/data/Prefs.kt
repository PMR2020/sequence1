package com.example.application_to_do_list.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.application_to_do_list.modele.Utilisateur
import com.google.gson.Gson

class Prefs (context: Context) {
    var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    var pseudo = prefs?.getString("pseudo","Pseudo").toString()
    var utilisateurs = prefs?.getString("utilisateurs","").toString()


    fun enregistrerPref(key : String, value : Any){
        val gsonset = Gson()
        val jsonset = gsonset.toJson(value)
        var editor= prefs?.edit()
        editor?.putString(key,jsonset)
        editor?.commit()



   }
}
