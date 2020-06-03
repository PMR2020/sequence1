package com.tppmr.sequenceoneedm.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.tppmr.sequenceoneedm.R
import com.tppmr.sequenceoneedm.adapter.ListeToDoAdapter
import com.tppmr.sequenceoneedm.classe.ItemToDo
import com.tppmr.sequenceoneedm.classe.ProfilListeToDo
import com.tppmr.sequenceoneedm.classe.ListeToDo

class ChoixListActivity : AppCompatActivity(), View.OnClickListener,
    ListeToDoAdapter.ActionListener {

    var adapter = newAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)
        setSupportActionBar(findViewById(R.id.toolbar))

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
        title = "${getString(R.string.listes)} $pseudoPrefs"
        val serializedJsonPrefs =
            prefs.getString(getString(R.string.serializedJsonSharedPreferences), "")
        val deserializedJsonPrefs: MutableList<ProfilListeToDo>

        if (serializedJsonPrefs != "") {
            deserializedJsonPrefs = deserializeJson(serializedJsonPrefs!!)
            val positionOfProfilListeToDo =
                getPositionOfProfilListeToDoForThisPseudo(pseudoPrefs!!, deserializedJsonPrefs)
            if (positionOfProfilListeToDo != -1) {
                adapter.showData(deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo)
            }
        }
    }

    private fun newAdapter(): ListeToDoAdapter {

        val adapter = ListeToDoAdapter(
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
     * Implémente le clique sur le bouton Ok pour enregistrer le nom de la nouvelle liste
     * et demander le nom du premier item de la liste
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.okBtn -> {
                val editText: EditText = findViewById(R.id.editText)
                val nameOfNewListeToDo = editText.text.toString()
                if (nameOfNewListeToDo.length > 0) {
                    editText.setText("")
                    val builder = AlertDialog.Builder(this)
                    val inflater = this.layoutInflater
                    val layoutDialog: View = inflater.inflate(R.layout.dialog_new, null)
                    val textView: TextView = layoutDialog.findViewById(R.id.textView)
                    textView.setText(getString(R.string.enterNewItem))
                    val newItemToDoEditText: EditText = layoutDialog.findViewById(R.id.editText2)
                    builder.setView(layoutDialog)
                        .setPositiveButton(R.string.ok,
                            DialogInterface.OnClickListener { dialog, id ->
                                val nameOfNewItemToDo = newItemToDoEditText.text.toString()
                                createNewListeToDo(nameOfNewListeToDo, nameOfNewItemToDo)
                                dialog.cancel()
                            })
                        .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { dialog, id ->
                                dialog.cancel()
                            })
                        .create()
                        .show()
                }
            }
        }
    }

    /**
     * Implémente le clique simple sur une liste pour passer à l'activité ShowListActivity qui
     * affiche tous les items de la liste
     */
    override fun onListeToDoClicked(listeToDo: ListeToDo, positionListeToDo: Int) {
        val intent = Intent(this, ShowListActivity::class.java)
        intent.putExtra("positionListeToDo", positionListeToDo)
        intent.putExtra("nomListeToDo", listeToDo.nomListeToDo)
        startActivity(intent)
    }

    /**
     * Implémente le clique long sur une liste pour soit modifier le nom de la liste soit la supprimer
     */
    override fun onListeToDoLongClicked(listeToDoPosition: Int) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val pseudoPrefs = prefs.getString(getString(R.string.pseudoSharedPreferences), "")
        var serializedJsonPrefs =
            prefs.getString(getString(R.string.serializedJsonSharedPreferences), "")
        var deserializedJsonPrefs: MutableList<ProfilListeToDo> =
            deserializeJson(serializedJsonPrefs!!)
        val positionOfProfilListeToDo =
            getPositionOfProfilListeToDoForThisPseudo(pseudoPrefs!!, deserializedJsonPrefs)

        val builder1 = AlertDialog.Builder(this)
        builder1.setTitle(getString(R.string.modifierOuSupprimerListeToDo))
            .setPositiveButton(R.string.modifier, DialogInterface.OnClickListener { dialog1, id ->
                val builder2 = AlertDialog.Builder(this)
                val inflater = this.layoutInflater
                val layoutDialog: View = inflater.inflate(R.layout.dialog_new, null)
                val textView: TextView = layoutDialog.findViewById(R.id.textView)
                textView.setText(getString(R.string.enterNameofTodoList))
                val newName: EditText = layoutDialog.findViewById(R.id.editText2)
                newName.setText(deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[listeToDoPosition].nomListeToDo)
                builder2.setView(layoutDialog)
                    .setPositiveButton(R.string.ok,
                        DialogInterface.OnClickListener { dialog2, id ->
                            val nameOfNewListeToDo = newName.text.toString()
                            changeName(
                                positionOfProfilListeToDo,
                                listeToDoPosition,
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
                            adapter.showData(deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo)
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
                val numberOfListeToDo = deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo.size
                if(numberOfListeToDo>1) {
                    deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo.remove(
                        deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo[listeToDoPosition]
                    )
                    serializedJsonPrefs = serializeJson(deserializedJsonPrefs)
                    with(prefs.edit()) {
                        putString(
                            getString(R.string.serializedJsonSharedPreferences),
                            serializedJsonPrefs
                        )
                        apply()
                        adapter.showData(deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo)
                    }
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
                dialog1.cancel()
            })
            .setNeutralButton(R.string.cancel, DialogInterface.OnClickListener { dialog1, id ->
                dialog1.cancel()
            })
            .create()
            .show()
    }

    /**
     * Change le nom de la liste
     */
    fun changeName(
        positionOfListOfTodoList: Int,
        todoListPosition: Int,
        newName: String,
        deserializedJson: MutableList<ProfilListeToDo>
    ) {
        deserializedJson[positionOfListOfTodoList].profilListeToDo[todoListPosition].nomListeToDo =
            newName
    }

    /**
     * Crée une nouvelle liste pour le profil actuel
     */
    fun createNewListeToDo(nameOfNewListeToDo: String, nameOfNewItemToDo: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val pseudoPrefs = prefs.getString(getString(R.string.pseudoSharedPreferences), "")
        var serializedJsonPrefs =
            prefs.getString(getString(R.string.serializedJsonSharedPreferences), "")
        var deserializedJsonPrefs: MutableList<ProfilListeToDo>
        var positionOfProfilListeToDo = 0

        if (serializedJsonPrefs == "") {
            deserializedJsonPrefs =
                createNewDeserializedJson(pseudoPrefs!!, nameOfNewListeToDo, nameOfNewItemToDo)
            startActivity(Intent(this,ChoixListActivity::class.java))
        } else {
            deserializedJsonPrefs = deserializeJson(serializedJsonPrefs!!)
            positionOfProfilListeToDo =
                getPositionOfProfilListeToDoForThisPseudo(pseudoPrefs!!, deserializedJsonPrefs)
            if (positionOfProfilListeToDo == -1) {
                createNewProfilListeToDoForThisPseudo(
                    pseudoPrefs,
                    nameOfNewListeToDo,
                    nameOfNewItemToDo,
                    deserializedJsonPrefs
                )
                positionOfProfilListeToDo = 0
                startActivity(Intent(this,ChoixListActivity::class.java))
            } else {
                addListeToDoForThisPseudo(
                    positionOfProfilListeToDo,
                    nameOfNewListeToDo,
                    nameOfNewItemToDo,
                    deserializedJsonPrefs
                )
            }
        }
        serializedJsonPrefs = serializeJson(deserializedJsonPrefs)
        with(prefs.edit()) {
            putString(getString(R.string.serializedJsonSharedPreferences), serializedJsonPrefs)
            apply()
        }
        adapter.showData(deserializedJsonPrefs[positionOfProfilListeToDo].profilListeToDo)
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
     * Crée un fichier désérialisé Json s'il n'existe pas encore
     */
    fun createNewDeserializedJson(
        pseudo: String,
        nameOfNewListeToDo: String,
        nameOfNewItemToDo: String
    ): MutableList<ProfilListeToDo> {
        return mutableListOf(
            ProfilListeToDo(
                pseudo,
                mutableListOf(
                    ListeToDo(
                        nameOfNewListeToDo,
                        mutableListOf(ItemToDo(nameOfNewItemToDo, false))
                    )
                )
            )
        )
    }

    /**
     * Ajoute au profil actuel encore vierge une nouvelle liste avec un item
     */
    fun createNewProfilListeToDoForThisPseudo(
        pseudo: String,
        nameOfNewListeToDo: String,
        nameOfNewItemToDo: String,
        deserializedJson: MutableList<ProfilListeToDo>
    ) {
        deserializedJson.add(
            ProfilListeToDo(
                pseudo,
                mutableListOf(
                    ListeToDo(
                        nameOfNewListeToDo,
                        mutableListOf(ItemToDo(nameOfNewItemToDo))
                    )
                )
            )
        )
    }

    /**
     * Ajoute au profil actuel ayant déjà au moins une liste une nouvelle liste avec un item
     */
    fun addListeToDoForThisPseudo(
        positionOfProfilListeToDo: Int,
        nameOfNewListeToDo: String,
        nameOfNewItemToDo: String,
        deserializedJson: MutableList<ProfilListeToDo>
    ) {
        deserializedJson[positionOfProfilListeToDo].profilListeToDo.add(
            ListeToDo(nameOfNewListeToDo, mutableListOf(ItemToDo(nameOfNewItemToDo)))
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


    /**
     * Implémente le clique sur le bouton retour pour forcer le passage à l'activité MainActivity
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(Intent(this, MainActivity::class.java))
            false
        } else super.onKeyDown(keyCode, event)
    }
}
