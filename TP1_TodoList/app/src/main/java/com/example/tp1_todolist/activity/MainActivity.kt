package com.example.tp1_todolist.activity

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.tp1_todolist.R
import com.example.tp1_todolist.model.Preference
import com.example.tp1_todolist.model.Profil
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)
        this.title="答答清单"

        val editTextPseudo:EditText=findViewById(R.id.editTextPseudo)
        val editTextPassword:EditText=findViewById(R.id.editTextPassword)
//        editTextPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD


        val btnMainOK:Button=findViewById(R.id.btnMainOK)
        btnMainOK.setOnClickListener(){
            var pseudo by Preference(this,"pseudo","pseudoDefault")
            pseudo=editTextPseudo.getText().toString()
            var password=editTextPassword.getText().toString()
            Log.i("MainActivity","pseudo: $pseudo   password: $password ")
            val profil: Profil =Profil(pseudo,password)
            var jsonProfil by Preference(this,pseudo, "jsonProfilDefault")
            if(jsonProfil=="jsonProfilDefault") {
                jsonProfil = Gson().toJson(profil)
            }
            Log.i("MainActivity","jsonProfil $jsonProfil")
            Log.i("MainActivity","btnMainOK clicked")
            val intentToChoixList:Intent=Intent(this@MainActivity,ChoixListActivity::class.java)
            startActivity(intentToChoixList)
        }
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_settings -> {
                val intentToChoixList:Intent=Intent(this@MainActivity,SettingsActivity::class.java)
                startActivity(intentToChoixList)
                true
            }
            R.id.action_logout -> {
                val t = Toast.makeText(this, "You have been logout", Toast.LENGTH_SHORT)
                t.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
