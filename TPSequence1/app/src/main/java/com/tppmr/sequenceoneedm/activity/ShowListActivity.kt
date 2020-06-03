package com.tppmr.sequenceoneedm.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tppmr.sequenceoneedm.R
import com.tppmr.sequenceoneedm.adapter.ItemToDoAdapter
import com.tppmr.sequenceoneedm.classe.ItemToDo
import com.tppmr.sequenceoneedm.classe.ProfilListeToDo

class ShowListActivity : AppCompatActivity(), View.OnClickListener,
    ItemToDoAdapter.ActionListener {

    var adapter = newAdapter()
    var nomListeToDo: String = ""
    var positionOfListeToDo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)
        setSupportActionBar(findViewById(R.id.toolbar))
        title = "Todo List"

        val intent = this.intent
        if (intent.hasExtra("positionListeToDo") && intent.hasExtra("nomListeToDo")) {
            positionOfListeToDo = intent.getIntExtra("positionListeToDo",1)
            nomListeToDo = intent.getStringExtra("nomListeToDo")
            title = nomListeToDo
        }

        val btnOk: Button = findViewById(R.id.okBtn)
        btnOk.setOnClickListener(this)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onStart() {
        super.onStart()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val pseudoPrefs = prefs.getString(getString(R.string.pseudoSharedPreferences), "")
        val serializedJsonPrefs =
            prefs.getString(getString(R.string.serializedJsonSharedPreferences), "")
        val deserializedJsonPrefs: MutableList<ProfilListeToDo>

        if (serializedJsonPrefs != "") {
            deserializedJsonPrefs = deserializeJson(serializedJsonPrefs!!)
            val positionOfProfilListeToDo =
                getPositionOfProfilListeToDoForThisPseudo(pseudoPrefs!!, deserializedJsonPrefs)
            if (positionOfProfilListeToDo != -1 && positionOfListeToDo != -1) {
                adapter.showData(deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo)
            }
        }
    }

    private fun newAdapter(): ItemToDoAdapter {

        val adapter = ItemToDoAdapter(
            actionListener = this
        )
        return adapter
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
     * Implémente le clique sur le bouton Ok pour enregistrer un nouvel item
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.okBtn -> {
                val editText: EditText = findViewById(R.id.editText)
                val nameOfNewItemToDo = editText.text.toString()
                if (nameOfNewItemToDo.length > 0){
                    editText.setText("")
                    createNewItemToDo(nameOfNewItemToDo)
                }
            }
        }
    }

    /**
     * Implémente le clique long sur un item pour soit modifier le nom de l'item soit le supprimer
     */
    override fun onItemToDoLongClicked(positionItemToDo: Int) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val pseudoPrefs = prefs.getString(getString(R.string.pseudoSharedPreferences), "")
        var serializedJsonPrefs =
            prefs.getString(getString(R.string.serializedJsonSharedPreferences), "")
        var deserializedJsonPrefs: MutableList<ProfilListeToDo> =
            deserializeJson(serializedJsonPrefs!!)
        val positionOfProfilListeToDo =
            getPositionOfProfilListeToDoForThisPseudo(pseudoPrefs!!, deserializedJsonPrefs)

        val builder1 = AlertDialog.Builder(this)
        builder1.setTitle(getString(R.string.modifierOuSupprimerItemToDo))
            .setPositiveButton(R.string.modifier, DialogInterface.OnClickListener { dialog1, id ->
                val builder2 = AlertDialog.Builder(this)
                val inflater = this.layoutInflater
                val layoutDialog: View = inflater.inflate(R.layout.dialog_new, null)
                val textView: TextView = layoutDialog.findViewById(R.id.textView)
                textView.setText(getString(R.string.enterNewItem))
                val newName: EditText = layoutDialog.findViewById(R.id.editText2)
                newName.setText(deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo[positionItemToDo].thingToDo)
                builder2.setView(layoutDialog)
                    .setPositiveButton(R.string.ok,
                        DialogInterface.OnClickListener { dialog2, id ->
                            val nameOfNewListeToDo = newName.text.toString()
                            changeName(
                                positionOfProfilListeToDo,
                                positionOfListeToDo,
                                positionItemToDo,
                                nameOfNewListeToDo,
                                deserializedJsonPrefs
                            )
                            serializedJsonPrefs = serializeJson(deserializedJsonPrefs)
                            with(prefs.edit()) {
                                putString(
                                    getString(R.string.serializedJsonSharedPreferences),
                                    serializedJsonPrefs
                                )
                                apply()
                            }
                            adapter.showData(deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo)
                            dialog1.cancel()
                            dialog2.cancel()
                        })
                    .setNegativeButton(R.string.cancel,
                        DialogInterface.OnClickListener { dialog2, id ->
                            dialog2.cancel()
                        })
                    .create()
                    .show()
                dialog1.cancel()
            })
            .setNegativeButton(R.string.supprimer, DialogInterface.OnClickListener { dialog1, id ->
                var deleteItem = true
                val numberOfItemToDo =
                    deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo.size
                if (!deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo[positionItemToDo].isDone) {
                    val builder3 = AlertDialog.Builder(this)
                    builder3.setTitle(getString(R.string.continuerOuAnnuler))
                        .setPositiveButton(
                            R.string.continuer,
                            DialogInterface.OnClickListener { dialog3, it ->
                                if (numberOfItemToDo > 1) {
                                    deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo.remove(
                                        deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo[positionItemToDo]
                                    )
                                    serializedJsonPrefs = serializeJson(deserializedJsonPrefs)
                                    with(prefs.edit()) {
                                        putString(
                                            getString(R.string.serializedJsonSharedPreferences),
                                            serializedJsonPrefs
                                        )
                                        apply()
                                    }
                                    adapter.showData(deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo)
                                } else {
                                    val numberOfListeToDo = deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo.size
                                    if(numberOfListeToDo>1) {
                                        deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo.remove(
                                            deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo]
                                        )
                                        serializedJsonPrefs = serializeJson(deserializedJsonPrefs)
                                        with(prefs.edit()) {
                                            putString(
                                                getString(R.string.serializedJsonSharedPreferences),
                                                serializedJsonPrefs
                                            )
                                            apply()
                                        }
                                        startActivity(Intent(this, ChoixListActivity::class.java))
                                    }
                                    else{
                                        deserializedJsonPrefs.remove(
                                            deserializedJsonPrefs[positionOfProfilListeToDo]
                                        )
                                        serializedJsonPrefs = serializeJson(deserializedJsonPrefs)
                                        with(prefs.edit()) {
                                            putString(getString(R.string.pseudoSharedPreferences), "")
                                            putString(
                                                getString(R.string.serializedJsonSharedPreferences),
                                                serializedJsonPrefs
                                            )
                                            apply()
                                        }
                                        startActivity(Intent(this, MainActivity::class.java))
                                    }
                                }
                                dialog3.cancel()
                            })
                        .setNegativeButton(
                            R.string.cancel,
                            DialogInterface.OnClickListener { dialog3, it ->
                                deleteItem = false
                                dialog3.cancel()

                            })
                        .create()
                        .show()
                } else {
                    if (numberOfItemToDo > 1) {
                        deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo.remove(
                            deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo[positionItemToDo]
                        )
                        serializedJsonPrefs = serializeJson(deserializedJsonPrefs)
                        with(prefs.edit()) {
                            putString(
                                getString(R.string.serializedJsonSharedPreferences),
                                serializedJsonPrefs
                            )
                            apply()
                        }
                        adapter.showData(deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo)
                    } else {
                        deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo.remove(
                            deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo]
                        )
                        serializedJsonPrefs = serializeJson(deserializedJsonPrefs)
                        with(prefs.edit()) {
                            putString(
                                getString(R.string.serializedJsonSharedPreferences),
                                serializedJsonPrefs
                            )
                            apply()
                        }
                        startActivity(Intent(this, ChoixListActivity::class.java))
                    }
                }
                dialog1.cancel()
            })
            .setNeutralButton(R.string.cancel, DialogInterface.OnClickListener { dialog1, id ->
                dialog1.cancel()
            })
            .create()
            .show()
    }

    /**
     * Implémente le clique sur la case à cocher à côté de l'item
     * pour changer l'état de l'item de fait à non fait ou inversement
     */
    override fun onItemToDoCheckBoxClicked(positionItemToDo: Int) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val pseudoPrefs = prefs.getString(getString(R.string.pseudoSharedPreferences), "")
        var serializedJsonPrefs =
            prefs.getString(getString(R.string.serializedJsonSharedPreferences), "")
        var deserializedJsonPrefs: MutableList<ProfilListeToDo> =
            deserializeJson(serializedJsonPrefs!!)
        val positionOfProfilListeToDo =
            getPositionOfProfilListeToDoForThisPseudo(pseudoPrefs!!, deserializedJsonPrefs)

        deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo[positionItemToDo].isDone =
            !deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo[positionItemToDo].isDone

        serializedJsonPrefs = serializeJson(deserializedJsonPrefs)
        with(prefs.edit()) {
            putString(getString(R.string.serializedJsonSharedPreferences), serializedJsonPrefs)
            apply()
        }
        adapter.showData(deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo)

    }

    /**
     * Change le nom de l'item
     */
    private fun changeName(
        positionOfProfilListeToDo: Int,
        positionOfListeToDo: Int,
        positionItemToDo: Int,
        newName: String,
        deserializedJson: MutableList<ProfilListeToDo>
    ) {
        deserializedJson[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo[positionItemToDo].thingToDo =
            newName
    }

    /**
     * Crée un nouvel item
     */
    fun createNewItemToDo(nameOfNewItemToDo: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val pseudoPrefs = prefs.getString(getString(R.string.pseudoSharedPreferences), "")
        var serializedJsonPrefs =
            prefs.getString(getString(R.string.serializedJsonSharedPreferences), "")
        var deserializedJsonPrefs: MutableList<ProfilListeToDo>

        if (serializedJsonPrefs != "") {
            var positionOfProfilListeToDo = 0
            deserializedJsonPrefs = deserializeJson(serializedJsonPrefs!!)
            positionOfProfilListeToDo =
                getPositionOfProfilListeToDoForThisPseudo(pseudoPrefs!!, deserializedJsonPrefs)
            if (positionOfProfilListeToDo != -1) {
                addItemToDoInListeToDo(
                    positionOfProfilListeToDo,
                    positionOfListeToDo,
                    nameOfNewItemToDo,
                    deserializedJsonPrefs
                )
            }
            serializedJsonPrefs = serializeJson(deserializedJsonPrefs)
            with(prefs.edit()) {
                putString(getString(R.string.serializedJsonSharedPreferences), serializedJsonPrefs)
                apply()
            }
            adapter.showData(deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo)
        }
    }

    /**
     * Renvoie la position du profil actuel dans le fichier désérialisé Json
     */
    fun getPositionOfProfilListeToDoForThisPseudo(
        pseudo: String,
        deserializedJson: MutableList<ProfilListeToDo>
    ): Int {
        var indice = -1
        for (i in 0 until deserializedJson.size) {
            if (deserializedJson[i].nomPseudo == pseudo) {
                indice = i
                break
            }
        }
        return indice
    }


    /**
     * Ajoute un item dans la liste
     */
    fun addItemToDoInListeToDo(
        positionOfProfilListeToDo: Int,
        positionOfListeToDo: Int,
        nameOfNewItemToDo: String,
        deserializedJson: MutableList<ProfilListeToDo>
    ) {
        deserializedJson[positionOfProfilListeToDo].profilListeToDo[positionOfListeToDo].listeToDo.add(
            ItemToDo(nameOfNewItemToDo)
        )
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
     * Sérialise un fichier désérialisé Json avec la librairie Gson
     */
    fun serializeJson(deserializedJson: MutableList<ProfilListeToDo>): String {
        val gson = GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create()
        return gson.toJson(deserializedJson)
    }
}
