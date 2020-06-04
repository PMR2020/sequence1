package com.example.tp_boukadir_v2

import android.content.ClipData
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
import com.example.tp_boukadir_v2.Adapter.ItemAdapter
import com.example.tp_boukadir_v2.Adapter.ListAdapter
import com.example.tp_boukadir_v2.ToDo.ItemToDo
import com.example.tp_boukadir_v2.ToDo.ListeToDo
import com.example.tp_boukadir_v2.ToDo.ProfilListToDo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class ShowList : GenericActivity() , ItemAdapter.ActionListener, View.OnClickListener{

    private var adapter : ItemAdapter? = null
    private var refBtnOK: Button? = null
    private var refListInput: EditText? = null
    private var prefs : SharedPreferences? = null
    private var pseudo : String? = null
    private var listeToDo : ListeToDo? = null
    private var filename : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)
        adapter = newAdapter()
        refBtnOK = findViewById(R.id.buttonShowList)
        refListInput = findViewById(R.id.buttonShowList)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        refBtnOK!!.setOnClickListener(this)

        val bundle = this.intent.extras
        pseudo = bundle!!.getString("pseudo")

        setRecyclerView()
    }
    private fun newAdapter() : ItemAdapter = ItemAdapter(actionListener = this)


    private fun setRecyclerView() {
        val list : RecyclerView = findViewById(R.id.showView)

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

        getPlayerList()
        val dataSet : List<ItemToDo>? = listeToDo?.LesItem
        adapter!!.setData(dataSet)
    }


    override fun onItemClicked(itemToDo: ItemToDo, fait : Boolean) {

        val listPlayer = getPlayerList()

        itemToDo.fait = fait

    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonShowList -> {
                val title = refListInput?.text.toString()
                if (listeToDo?.rechercherItem(title) == null){
                    listeToDo?.ajouterItem(ItemToDo(title,false))

                    val dataSet : MutableList<ItemToDo> = listeToDo!!.LesItem
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
        listeToDo = null
        listPlayer.forEach { profilListe ->
            if (profilListe.login == pseudo) {
                listPlayer.remove(profilListe)
            }
        }
        return listPlayer
    }

}
