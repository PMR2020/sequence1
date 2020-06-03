package com.example.tp1_todolist.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceScreen
import com.example.tp1_todolist.R
import com.example.tp1_todolist.fragment.MySettingsFragment
import com.example.tp1_todolist.model.Preference as MyPreference

class SettingsActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("SettingsActivity","onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_activity)
        this.title="Setting"
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, MySettingsFragment())
            .commit()

        var pseudo by MyPreference(this, "pseudo", "pseudoDefault")

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this )
        val pseudoInSetting = sharedPreferences.getString("pseudo", pseudo)


        fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?) {
            val t = Toast.makeText(this, "You have been logout", Toast.LENGTH_SHORT)
            t.show()
        }


    }





}




