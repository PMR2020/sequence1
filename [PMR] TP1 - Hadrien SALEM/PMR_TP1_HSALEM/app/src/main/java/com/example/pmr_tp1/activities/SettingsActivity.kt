package com.example.pmr_tp1.activities

import android.os.Bundle
import android.preference.ListPreference
import android.preference.PreferenceActivity
import android.preference.PreferenceCategory
import android.preference.PreferenceManager
import android.util.Log
import android.widget.EditText
import com.example.pmr_tp1.R
import com.example.pmr_tp1.utils.UserManager
import com.example.pmr_tp1.utils.UserManager.DataInitalise.DEBUG_LOGIN


class SettingsActivity : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screen = preferenceManager.createPreferenceScreen(this)


        addPreferencesFromResource(R.layout.activity_settings)

        val category = PreferenceCategory(this)
        category.title = "Préférences de l'application"

        screen.addPreference(category)

        val customListPref : ListPreference  = ListPreference(this)

        val dummyManager = UserManager(DEBUG_LOGIN, this)

        val entries =
            dummyManager.getAllUserNames()
        val entryValues =
            dummyManager.getAllUserNames()
        customListPref.setEntries(entries)
        customListPref.setEntryValues(entryValues)

        customListPref.setTitle("Choisir un pseudo existant")
        customListPref.key = "listPref"

        category.addPreference(customListPref)
        preferenceScreen = screen


        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val selectedPseudo= prefs.getString("listPref","none")

        Log.i("SNOW", "Selected pseudo : " + selectedPseudo)

    }
}