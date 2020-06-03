package com.example.listedroulante.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listedroulante.R
import com.example.listedroulante.adapter.ListAdapter
import com.example.listedroulante.utility.TaskList
import com.example.listedroulante.utility.User
import com.example.listedroulante.utility.UserList

class ChoiceActivity : AppCompatActivity(), View.OnClickListener { //, ListAdapter.OnNoteListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lists)

        var btnCreate: Button = findViewById(R.id.createList)
        btnCreate.setOnClickListener(this)

        val userList = UserList
        userList.listOfUser.add(User("Gu"))

        val a = TaskList("Liste 1", "1ere liste")
        val b = TaskList("Liste 2", "2eme liste")
        val c = TaskList("Liste 3", "3eme liste")
        val d = TaskList("Liste 4", "4eme liste")
        val e = TaskList("Liste 5", "Desc 5")
        val f = TaskList("Liste 6", "Desc 6")
        val g = TaskList("Liste 7", "Desc 7")
        val h = TaskList("Liste 8", "Desc 8")
        val i = TaskList("Liste 9", "Desc 9")
        val j = TaskList("Liste 10", "Desc 10")
        val k = TaskList("Liste 11", "Desc 11")
        val l = TaskList("Liste 12", "Desc 12")
        //var listExemple
        userList.listOfUser[0].listOfList = mutableListOf(a,b,c,d,e,f,g,h,i,j,k,l)


        //OnClickListener//
        fun listItemClicked (listName : String) {
            Toast.makeText(this, "Clicked : $listName" ,Toast.LENGTH_LONG).show()

        }

        //RecyclerView//
        val listTask : RecyclerView = findViewById(R.id.rv_listTask)
        val adapter = ListAdapter(userList.listOfUser[0].listOfList, { listName : String -> listItemClicked(listName) })
        listTask.adapter = adapter
        listTask.layoutManager = LinearLayoutManager(this)


    }

    fun fooText2 (view : View) {
        alerter("Enter the name of the list")
    }

    fun fooText3 (view : View) {
        alerter("Describe the list")
    }

    fun alerter (texte : String) {
        Log.i ("CAT", texte)
        var t : Toast = Toast.makeText(this, texte, Toast.LENGTH_SHORT)
        t.show()
    }

    override fun onClick(v: View?) {
        var askName : EditText = findViewById(R.id.taskListName)
        var name : String = askName.text.toString()
        var askDesc : EditText = findViewById(R.id.taskListDescr)
        var desc : String = askDesc.text.toString()
        when (v?.id) {
            R.id.createList -> {
                //userList.listOfUser[0].listOfList.add((name))
                alerter("List $name, $desc added")
                val intent = Intent (this, TaskActivity::class.java)
                startActivity(intent)
            }

        }
    }
}