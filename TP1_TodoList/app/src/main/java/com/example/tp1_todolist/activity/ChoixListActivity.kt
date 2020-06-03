package com.example.tp1_todolist.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1_todolist.R
import com.example.tp1_todolist.adapter.ItemAdapterList
import com.example.tp1_todolist.model.ItemList
import com.example.tp1_todolist.model.Preference
import com.example.tp1_todolist.model.Profil
import com.google.gson.Gson
import org.w3c.dom.Text


class ChoixListActivity:AppCompatActivity(),ItemAdapterList.ActionListener {
    var dataSet=mutableListOf<ItemList>()//？？ 列表总是同一个，变化的是里面的元素


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choix_list_activity)

        var pseudo by Preference(this,"pseudo","pseudoDefault")
        this.title="$pseudo's List"
        var jsonProfil by Preference(this,pseudo,"jsonProfilDefault")
        val myProfil: Profil = Gson().fromJson(jsonProfil, Profil::class.java)
        dataSet=myProfil.myList
        Log.i("ChoixListActivity","dataSet is $dataSet")
        Log.i("ChoixListActivity","myProfil is $myProfil")
        Log.i("ChoixListActivity","jsonProfil is $jsonProfil")
        val rvList:RecyclerView = findViewById(R.id.rv_list)
        val btnNouvelleList: Button =findViewById(R.id.btnNouvelleList)
        val etNouvelleList:EditText=findViewById(R.id.etNouvelleList)
        rvList.adapter=newAdapter(dataSet)
        rvList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        btnNouvelleList.setOnClickListener(){
            Log.i("ChoixListActivity","btnNouvelleList is clicked")
            val listTitle:String=etNouvelleList.getText().toString()
            Log.i("ChoixListActivity","listTitle is $listTitle")

            if(listTitle!=""){
//                dataSet.add(ItemList(listTitle))？？
                myProfil.myList.add(ItemList(listTitle))
                jsonProfil= Gson().toJson(myProfil)
                Log.i("ChoixListActivity","aftrer click btnNouvelle, dataSet is $dataSet")
                Log.i("ChoixListActivity","aftrer click btnNouvelle, myProfil is $myProfil")
                Log.i("ChoixListActivity","aftrer click btnNouvelle, jsonProfil is $jsonProfil")
                (rvList.adapter as ItemAdapterList).notifyDataSetChanged()
                etNouvelleList.setText("")
            }else{
                val t = Toast.makeText(this, "A list name is need", Toast.LENGTH_SHORT)
                t.show()
            }
        }


    }

    private fun newAdapter(dataSet:MutableList<ItemList>): ItemAdapterList {
        val adapter = ItemAdapterList(
            dataSet=dataSet,
            actionlistener=this//将ChoixListActivity implementer为ItemAdapter.ActionListener
        )
        return adapter
    }


    override fun onItemClicked(listPosition: Int,listTitle:String) {
        Log.i("onItemClickedList","listPosition is $listPosition")
        val bundle:Bundle= Bundle()
        bundle.putInt("listPosition",listPosition)
        bundle.putString("listTitle",listTitle)
        val intentToShowListActivity:  Intent= Intent(this@ChoixListActivity,ShowListActivity::class.java)
        intentToShowListActivity.putExtras(bundle)
        startActivity(intentToShowListActivity)
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
                val intentToChoixList:Intent=Intent(this@ChoixListActivity,SettingsActivity::class.java)
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