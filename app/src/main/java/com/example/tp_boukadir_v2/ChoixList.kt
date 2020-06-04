package com.example.tp_boukadir_v2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_boukadir_v2.Adapter.ListAdapter
import com.example.tp_boukadir_v2.ToDo.ListeToDo
import com.example.tp_boukadir_v2.ToDo.ProfilListToDo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

class ChoixList : GenericActivity() ,ListAdapter.ActionListener, View.OnClickListener{

    private var adapter : ListAdapter? = null
    private var refBtnOK: Button? = null
    private var refListInput: EditText? = null
    private var prefs : SharedPreferences? = null
    private var pseudo : String? = null
    private var profilListeToDo : ProfilListToDo? = null
    private var filename : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)

        adapter = newAdapter()
        refBtnOK = findViewById(R.id.buttonChoixList)
        refListInput = findViewById(R.id.NewListInput)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        refBtnOK!!.setOnClickListener(this)

        val bundle = this.intent.extras
        pseudo = bundle!!.getString("pseudo")

        setRecyclerView()
    }
    private fun newAdapter() : ListAdapter = ListAdapter(actionListener = this)


    private fun setRecyclerView() {
        val list : RecyclerView = findViewById(R.id.choixView)

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

        getPlayerList()
        val dataSet : List<ListeToDo>? = profilListeToDo?.mesListeToDo
        adapter!!.setData(dataSet)
    }


    override fun onItemClicked(listeToDo: ListeToDo) {
        val bundle = Bundle()
        bundle.putSerializable("liste", listeToDo)
        bundle.putSerializable("profilListe", profilListeToDo)

        val intent = Intent(this, ShowList::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonChoixList -> {
                val title = refListInput?.text.toString()
                if (profilListeToDo?.rechercherListe(title) == null){
                    profilListeToDo?.ajouterListe(ListeToDo(title))

                    val dataSet : MutableList<ListeToDo>?  = profilListeToDo?.mesListeToDo
                    adapter!!.setData(dataSet)
                }
                else {
                    Toast.makeText(applicationContext,"Cette liste existe déjà !", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun getPlayerList() : MutableList<ProfilListToDo> {

        val file = File(filesDir, filename!!)
        val jsonProfiles : String = file.readText()
        val gson = Gson()
        val listPlayerType = object : TypeToken<List<ProfilListToDo>>() {}.type
        var listPlayer : MutableList<ProfilListToDo>? = gson.fromJson(jsonProfiles, listPlayerType)
        if (listPlayer == null) {
            listPlayer = mutableListOf()
        }
        // Select the correct ProfilListeToDo or create it if it doesn't already exist
        profilListeToDo = null
        listPlayer.forEach { profilListe ->
            if (profilListe.login == pseudo) {
                profilListeToDo = profilListe
                listPlayer.remove(profilListe)
            }
        }
        if (profilListeToDo == null) {
            profilListeToDo = pseudo?.let { ProfilListToDo(it) }
        }
        return listPlayer
    }
}




