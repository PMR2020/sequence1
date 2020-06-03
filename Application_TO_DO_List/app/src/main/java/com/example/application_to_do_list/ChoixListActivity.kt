package com.example.application_to_do_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.application_to_do_list.affichage.ListesAdapter
import com.example.application_to_do_list.data.Prefs
import com.example.application_to_do_list.modele.Liste
import com.example.application_to_do_list.modele.Utilisateur
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_choix_list.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileWriter


class ChoixListActivity : AppCompatActivity(), View.OnClickListener, ListesAdapter.ActionListener {

    var pseudo=""
    var user : Utilisateur? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)

        //récupérer bundle contenant pseudo
        var bundle :Bundle=this.intent.extras
        pseudo = bundle.getString("pseudo")
        user = Utilisateur.getUser(pseudo)
        this.setTitle("Listes enregistrées")
        majListe()
        //RECUPERER L'ECOUTE DES CLICS SUR LE BOUTON
        btnListe.setOnClickListener(this)

    }


    private fun majListe() {
        //Création vertical layout manger
        recyclerListe.layoutManager = LinearLayoutManager(this)
        //Accéder à l'adapter
        recyclerListe.adapter = ListesAdapter(user!!.listes, actionListener = this)
        // MAJ EDT
        edtListe.setHint("Nouvelle liste")
        saveData()
    }

    // CREATION DU MENU ANDROID
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item.toString()=="Préférences"){
            saveData()
            startActivity(Intent(this,SettingsActivity::class.java))
        }
        return true
    }

    // CLIC SUR UNE LISTE
    override fun onListClicked(liste: Liste) {
        var ListActivity : Intent = Intent(this,ShowListActivity::class.java)
        ListActivity.putExtra("pseudo",user?.pseudo)
        ListActivity.putExtra("liste",liste.title)
        startActivity(ListActivity)
    }

    fun saveData(){
        var preferences = Prefs(this)
        preferences?.enregistrerPref("pseudo",pseudo)
        preferences?.enregistrerPref("utilisateurs",Utilisateur.utilisateurs)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        saveData()
    }

    // CLIC BOUTON
    override fun onClick(v: View?) {
        //Nouvelle liste
        if (v?.id == R.id.btnListe) {
            if(edtListe.text.toString()!=""){
                user?.ajoutListe(Liste(edtListe.text.toString()))
            }else{
                user?.ajoutListe(Liste(edtListe.hint.toString()))
            }

            majListe()
        }
    }
}




