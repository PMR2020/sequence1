package com.example.application_to_do_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.application_to_do_list.affichage.ItemsAdapter
import com.example.application_to_do_list.data.Prefs
import com.example.application_to_do_list.modele.Item
import com.example.application_to_do_list.modele.Liste
import com.example.application_to_do_list.modele.Utilisateur
import kotlinx.android.synthetic.main.activity_show_list.*

class ShowListActivity : AppCompatActivity(), View.OnClickListener, ItemsAdapter.ActionListener {
    var pseudo=""
    lateinit var user : Utilisateur
    lateinit var liste : Liste

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)

        //récupérer bundle contenant pseudo
        var bundle :Bundle=this.intent.extras
        pseudo = bundle.getString("pseudo")
        user = Utilisateur.getUser(pseudo)
        liste = user.getListe(bundle.getString("liste"))
        this.setTitle(liste.title)
        majItems()
        // RECUPERER L'ECOUTE DES CLICS SUR LE BOUTON
        btnItem.setOnClickListener(this)
    }

    private fun majItems() {
        //Création vertical layout manger
        recyclerItems.layoutManager = LinearLayoutManager(this)
        //Accéder à l'adapter
        recyclerItems.adapter = ItemsAdapter(liste,this)
        saveData()
    }
    fun saveData(){
        var preferences = Prefs(this)
        preferences?.enregistrerPref("utilisateurs",Utilisateur.utilisateurs)
        Log.d("aaa",Utilisateur.utilisateurs.toString())
    }

    override fun onBackPressed() {
        super.onBackPressed()
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
    override fun onItemClicked(item: Item) {
        item.chgtStatut()
        saveData()
    }
    // CLIC BOUTON
    override fun onClick(v: View?) {
        if (v?.id==R.id.btnItem){
            if(edtItem.text.toString()!=""){
                liste.ajoutItem(Item(edtItem.text.toString()))
            }else{
                liste.ajoutItem(Item(edtItem.hint.toString()))
            }

            majItems()
        }
    }
}
