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
import com.example.tp1_todolist.adapter.ItemAdapterTask
import com.example.tp1_todolist.model.ItemList
import com.example.tp1_todolist.model.ItemTask
import com.example.tp1_todolist.model.Preference
import com.example.tp1_todolist.model.Profil
import com.google.gson.Gson

class ShowListActivity :AppCompatActivity(),ItemAdapterTask.ActionListener {
    var dataSetTask=mutableListOf<ItemTask>()//？？ 列表总是同一个，变化的是里面的元素
    var listPosition:Int=0
    var listTiltle:String="ShowListActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_list_activity)

        val bundle=intent.extras
        listPosition= bundle?.getInt("listPosition")!!
        listTiltle=bundle?.getString("listTitle")!!
        this.title=listTiltle

        var pseudo by Preference(this,"pseudo","pseudoDefault")
        var jsonProfil: String by Preference(this,pseudo,"jsonProfilDefault")
        var myProfil: Profil = Gson().fromJson(jsonProfil, Profil::class.java)
        dataSetTask=myProfil.myList[listPosition].myTask
        Log.i("ShowListActivity","listPosition is $listPosition")
        Log.i("ShowListActivity","myList is ${myProfil.myList[listPosition]}")
        Log.i("ShowListActivity","dataSetTask is $dataSetTask")
        Log.i("ShowListActivity","myProfil is $myProfil")
        Log.i("ShowListActivity","jsonProfil is $jsonProfil")
        val rvTask: RecyclerView = findViewById(R.id.rv_task)
        val btnNouvelleTask: Button =findViewById(R.id.btnNouvelleTask)
        val etNouvelleTask: EditText =findViewById(R.id.etNouvelleTask)
        rvTask.adapter = newAdapter(dataSetTask)
        rvTask.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        btnNouvelleTask.setOnClickListener(){
            Log.i("ShowListActivity","btnNouvelleTask is clicked")
            val taskDescription:String=etNouvelleTask.getText().toString()
            Log.i("ShowListActivity","Task description is $taskDescription")

            if(taskDescription!=""){
                myProfil.myList[listPosition!!].myTask.add(ItemTask(taskDescription))
                jsonProfil= Gson().toJson(myProfil)
                Log.i("ShowListActivity","aftrer click btnNouvelle, dataSetTask is $dataSetTask")
                Log.i("ShowListActivity","aftrer click btnNouvelle, myList is ${myProfil.myList[listPosition!!]}")
                Log.i("ShowListActivity","aftrer click btnNouvelle, myProfil is $myProfil")
                Log.i("ShowListActivity","aftrer click btnNouvelle, jsonProfil is $jsonProfil")
                (rvTask.adapter as ItemAdapterTask).notifyDataSetChanged()
                etNouvelleTask.setText("")
            }else{
                val t = Toast.makeText(this, "A task name is need", Toast.LENGTH_SHORT)
                t.show()
            }
        }
    }

    private fun newAdapter(dataSetTask:MutableList<ItemTask>): ItemAdapterTask {
        val adapter = ItemAdapterTask(
            dataSetTask=dataSetTask,
            actionlistener=this//将ChoixListActivity implementer为ItemAdapter.ActionListener
        )
        return adapter
    }


    override fun onItemClicked(taskPosition: Int) {
        var pseudo by Preference(this,"pseudo","pseudoDefault")
        var jsonProfil: String by Preference(this,pseudo,"jsonProfilDefault")
        val myProfil: Profil = Gson().fromJson(jsonProfil, Profil::class.java)
        val taskClickd:ItemTask=myProfil.myList[listPosition].myTask[taskPosition]
        Log.i("onItemClicked","mylistPosition is $listPosition")
        Log.i("onItemClicked","taskPosition is $taskPosition")
        Log.i("onItemClicked","myTask is ${myProfil.myList[listPosition].myTask}")
        Log.i("onItemClicked","dataSetTask is $dataSetTask")
        Log.i("onItemClicked","myProfil is $myProfil")
        Log.i("onItemClicked","jsonProfil is $jsonProfil")
        myProfil.myList[listPosition].myTask[taskPosition].fait = !taskClickd.fait
        jsonProfil=Gson().toJson(myProfil)
        Log.i("onItemClicked","after click mylistPosition is $listPosition")
        Log.i("onItemClicked","after click taskPosition is $taskPosition")
        Log.i("onItemClicked","after click myTask is ${myProfil.myList[listPosition].myTask}")
        Log.i("onItemClicked","after click dataSetTask is $dataSetTask")
        Log.i("onItemClicked","after click myProfil is $myProfil")
        Log.i("onItemClicked","after click jsonProfil is $jsonProfil")
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
                val intentToChoixList:Intent=Intent(this@ShowListActivity,SettingsActivity::class.java)
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