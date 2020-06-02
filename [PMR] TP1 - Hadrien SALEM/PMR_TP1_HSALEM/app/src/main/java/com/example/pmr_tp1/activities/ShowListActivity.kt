package com.example.pmr_tp1.activities

import android.content.Intent
import android.os.Bundle
import android.os.UserManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pmr_tp1.R
import com.example.pmr_tp1.adapter.ChoixListeAdapter
import com.example.pmr_tp1.adapter.ShowListAdapter
import com.example.pmr_tp1.lists.ItemToDo
import com.example.pmr_tp1.lists.ListeToDo
import com.example.pmr_tp1.lists.ProfilListeToDo

class ShowListActivity : AppCompatActivity(), ShowListAdapter.ShowListListener, View.OnClickListener{

    var login = ""
    var indexOfList = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) // to prevent keyboard from showing up
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showlist)

        login = this.intent.extras!!.getString("login")!!
        indexOfList = this.intent.extras!!.getInt("indexOfList")
        val userManager : com.example.pmr_tp1.utils.UserManager = this.intent.extras!!.getSerializable("userManager")!! as com.example.pmr_tp1.utils.UserManager
        val liste = userManager.profile.mesListesToDo[indexOfList]

        findViewById<TextView>(R.id.list_name_display).setText("${liste.titreListeToDo}")

        // Adding a listener to the add button
        val addItemBtn = findViewById<Button>(R.id.ajouter_item_btn)
        addItemBtn.setOnClickListener(this)

        // RecyclerView
        val adapter = ShowListAdapter(this)
        val listOfItemsRecyclerView = findViewById<RecyclerView>(R.id.list_of_items)
        listOfItemsRecyclerView.adapter = adapter
        listOfItemsRecyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        adapter.showData(liste.lesItems)

    }

    override fun onItemClicked(indexOfItem : Int, isChecked : Boolean) {
        Log.i("SNOW","onItemClicked called")
        val userManager = com.example.pmr_tp1.utils.UserManager(login, this)
        userManager.changeItemState(indexOfList, indexOfItem, isChecked)
    }

    override fun onClick(v: View?) {
        val userManager = com.example.pmr_tp1.utils.UserManager(login, this)
        val itemDescription = findViewById<EditText>(R.id.item_description_input).text.toString()
        userManager.createNewItem(itemDescription, indexOfList)

        // Reloading the activity to display changes
        val b = Bundle()
        b.putString("login", login)
        b.putSerializable("userManager", userManager)
        b.putInt("indexOfList", indexOfList)
        val toShowListActivity = Intent(this, ShowListActivity::class.java)
        toShowListActivity.putExtras(b)
        finish()
        startActivity(toShowListActivity)

    }
}
