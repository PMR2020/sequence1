@file:Suppress("DEPRECATION")

package com.tppmr.sequenceoneedm.activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceActivity
import android.view.KeyEvent
import android.view.View
import androidx.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tppmr.sequenceoneedm.R
import com.tppmr.sequenceoneedm.classe.ProfilListeToDo

class SettingsActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.preferences)
        addPreferencesFromResource(R.xml.preferences)
    }

    /**
     * Implémente le clique sur le bouton EffacerTout pour supprimer toutes les données enregistrées
     * dans les préférences partagées
     */
    fun onButtonClick(view: View) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        var serializedJsonPrefs =
            prefs.getString(getString(R.string.serializedJsonSharedPreferences), "")
        if (serializedJsonPrefs != "") {
            var deserializedJsonPrefs: MutableList<ProfilListeToDo> =
                deserializeJson(serializedJsonPrefs!!)
            deserializedJsonPrefs.clear()
            serializedJsonPrefs = serializeJson(deserializedJsonPrefs)
            with(prefs.edit()) {
                putString(getString(R.string.pseudoSharedPreferences), "")
                putString(
                    getString(R.string.serializedJsonSharedPreferences),
                    serializedJsonPrefs
                )
                apply()
            }
        }
    }

    /**
     * Désérialise un fichier sérialisé Json avec la librairie Gson
     */
    fun deserializeJson(serializedJson: String): MutableList<ProfilListeToDo> {
        val gson = GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create()
        val jsonType = object : TypeToken<MutableList<ProfilListeToDo>>() {}.type
        return gson.fromJson(serializedJson, jsonType)
    }

    /**
     * Sérialise un fichier désérialisé Json avec la librairie Gson
     */
    fun serializeJson(deserializedJson: MutableList<ProfilListeToDo>): String {
        val gson = GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create()
        return gson.toJson(deserializedJson)
    }

    /**
     * Implémente le clique sur le bouton retour pour forcer le passage à l'activité MainActivity
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(Intent(this, MainActivity::class.java))
            false
        } else super.onKeyDown(keyCode, event)
    }

}
