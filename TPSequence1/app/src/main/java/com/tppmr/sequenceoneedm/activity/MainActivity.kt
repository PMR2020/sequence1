package com.tppmr.sequenceoneedm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tppmr.sequenceoneedm.R
import com.tppmr.sequenceoneedm.classe.ProfilListeToDo

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        title = ""
        val btnOk: Button = findViewById(R.id.okBtn)
        btnOk.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        val pseudos = getAllPseudoAlreadyUsed()
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line, pseudos
        )
        val autoCompleteTextView =
            findViewById(R.id.autoCompleteTextView) as AutoCompleteTextView
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setText(
            PreferenceManager.getDefaultSharedPreferences(this).getString(
                getString(R.string.pseudoSharedPreferences), ""
            )
        )
    }

    /**
     * Inflate le menu de l'activité avec le layout menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    /**
     * Implémente le clique sur l'item du menu pour passer à l'activité SettingsActivity
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    /**
     * Implémente le clique sur le bouton Ok pour enregistrer le pseudo dans les préférences et pour
     * passer à l'activité ChoixListActivity
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.okBtn -> {
                val autoCompleteTextView: EditText = findViewById(R.id.autoCompleteTextView)
                val pseudo = autoCompleteTextView.text.toString()
                if (pseudo.length > 0) {
                    val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
                    with(sharedPref.edit()) {
                        putString(getString(R.string.pseudoSharedPreferences), pseudo)
                        apply()
                    }
                    startActivity(Intent(this, ChoixListActivity::class.java))
                }
            }
        }
    }

    /**
     * Récupère tous les pseudos en cours d'utilisation
     */
    fun getAllPseudoAlreadyUsed(): Array<String> {
        val pseudos: Array<String>
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val pseudoPrefs = prefs.getString(getString(R.string.pseudoSharedPreferences), "")
        var serializedJsonPrefs =
            prefs.getString(getString(R.string.serializedJsonSharedPreferences), "")
        var deserializedJsonPrefs: MutableList<ProfilListeToDo>
        if (serializedJsonPrefs == "") {
            pseudos = emptyArray()
        } else {
            val temp = mutableListOf<String>()
            deserializedJsonPrefs = deserializeJson(serializedJsonPrefs!!)
            for (i in 0 until deserializedJsonPrefs.size) {
                temp.add(deserializedJsonPrefs[i].nomPseudo)
            }
            pseudos = temp.toTypedArray()
        }
        return pseudos
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
     * Implémente le clique sur le bouton retour pour empêcher le retour sur l'activité précédente
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            false
        } else super.onKeyDown(keyCode, event)
    }

}
