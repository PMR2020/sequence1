package com.example.pmr_tp1.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pmr_tp1.R
import com.example.pmr_tp1.adapter.ChoixListeAdapter
import com.example.pmr_tp1.lists.ListeToDo
import com.example.pmr_tp1.utils.UserManager

class ChoixListActivity : AppCompatActivity(), ChoixListeAdapter.ListOfListsListener, View.OnClickListener{

    var login : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) // to prevent keyboard from showing up
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choixliste)

        this.login = this.intent.extras!!.getString("login")!!
        findViewById<TextView>(R.id.usernamedisplay).setText("Listes de $login")

        // Adding a listener to the add button
        val addListBtn = findViewById<Button>(R.id.ajouter_liste_btn)
        addListBtn.setOnClickListener(this)

        // Creating a profile object and getting the list names to display
        val userManager = UserManager(login, this)
        val currentProfile = userManager.manageUser()
        val listNamesList = currentProfile.mesListesToDo

        // RecyclerView
        val rvAdapter = ChoixListeAdapter(this)
        val listOfListsRecyclerView = findViewById<RecyclerView>(R.id.list_of_lists)
        listOfListsRecyclerView.adapter = rvAdapter
        listOfListsRecyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        rvAdapter.showData(listNamesList)

    }

    override fun onListClicked(listeToDo: ListeToDo, indexOfList : Int) {
        // Going to ShowListActivity, giving the list object, the name of its owner, and its userManager
        val b = Bundle()
        b.putString("login", login)
        b.putSerializable("userManager", UserManager(login, this))
        b.putInt("indexOfList", indexOfList)
        val toShowListActivity = Intent(this, ShowListActivity::class.java)
        toShowListActivity.putExtras(b)
        startActivity(toShowListActivity)
    }

    override fun onClick(v: View?) {
        val userManager = UserManager(login, this)
        val listName = findViewById<EditText>(R.id.list_name_input).getText().toString()
        userManager.createNewList(listName)

        // Reloading the activity so that modifications appear
        val b = Bundle()
        b.putString("login", login)
        val toChoixListActivity = Intent(this, ChoixListActivity::class.java)
        toChoixListActivity.putExtras(b)
        finish()
        startActivity(toChoixListActivity)
    }
}