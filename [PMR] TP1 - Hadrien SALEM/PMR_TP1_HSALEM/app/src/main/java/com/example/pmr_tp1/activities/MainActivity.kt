package com.example.pmr_tp1.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.pmr_tp1.R

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "SNOW"
    var pseudoInput = "Name"


    override fun onRestart() {
        super.onRestart()
        finish();
        startActivity(getIntent());
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val selectedPseudo= prefs.getString("listPref","Votre login")
        Log.i("SNOW", "Selected pseudo : " + selectedPseudo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Adding the activity as listener for the button
        val pseudoOKBtnReference = findViewById<Button>(R.id.pseudo_ok_btn)
        pseudoOKBtnReference.setOnClickListener(this)


        // Using Preferences to determine the written name
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val selectedPseudo= prefs.getString("listPref","NameDefault")
        Log.i("SNOW", "Selected pseudo : " + selectedPseudo)

        // Last pseudo becomes the one that was written
        pseudoInput = findViewById<EditText>(R.id.pseudo_input).getText().toString()
        findViewById<EditText>(R.id.pseudo_input).setText(selectedPseudo)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.pseudo_ok_btn ->{
                Log.i(TAG,"ok_pseudo pushed")

                pseudoInput = findViewById<EditText>(R.id.pseudo_input).getText().toString()

                // Last pseudo becomes the one that was written
                findViewById<EditText>(R.id.pseudo_input).setText(pseudoInput)

                // Going to ChoixListActivity, giving pseudo
                val b = Bundle()
                b.putString("login", pseudoInput)
                val toChoixListActivity = Intent(this, ChoixListActivity::class.java)
                toChoixListActivity.putExtras(b)

                startActivity(toChoixListActivity)
            }
        }
    }

}
