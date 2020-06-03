package com.example.application_to_do_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.application_to_do_list.data.Prefs
import com.example.application_to_do_list.modele.Utilisateur
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.FileWriter


class MainActivity : AppCompatActivity(), View.OnClickListener {
    var preferences: Prefs? = null
    var pseudo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setTitle("Saisie de l'utilisateur")
        preferences = Prefs(this)
        Log.d("aaa",pseudo)
        val gson = Gson()
        val mutableListUtilisateurType = object : TypeToken<MutableList<Utilisateur>>() {}.type
        Utilisateur.utilisateurs = gson.fromJson(preferences?.utilisateurs,mutableListUtilisateurType)
        pseudo = gson.fromJson(preferences?.pseudo,String::class.java)
        edtPseudo.hint=pseudo


        //RECUPERER L'ECOUTE DES CLICS
        btnPseudo.setOnClickListener(this)
    }


    //ECRITURE DES PREFERENCES
    private fun entreePseudo(pseudo: String) {
        if(pseudo!=""){
            preferences?.enregistrerPref("pseudo",pseudo)
            preferences?.enregistrerPref("utilisateurs",Utilisateur.utilisateurs)

        }
    }


    // CREATION DU MENU ANDROID
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item.toString()=="Préférences"){
            // Enregister pseudo
            entreePseudo(edtPseudo.text.toString())
            newActivity(Intent(this,SettingsActivity::class.java))
        }
        return true
    }

    fun newActivity(intent : Intent) {
        // Récupération de l'user
        if(edtPseudo.text.toString()!=""){
            //ouverture Activité ChoixListActivity
            intent.putExtra("pseudo",edtPseudo.text.toString())
        }else{
            intent.putExtra("pseudo",edtPseudo.hint.toString())
        }

        //ouverture Activité ChoixListActivity
        startActivity(intent)
    }

    // ACTIONS A REALISER SI EVENEMENT CLIC
    override fun onClick(v: View?) {
        //Nouvelle Activité ChoixListActivity
        if(v?.id == R.id.btnPseudo) {
            newActivity(Intent(this,ChoixListActivity::class.java))
        }
    }
}
