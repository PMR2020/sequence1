package com.example.listedroulante.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listedroulante.R
import com.example.listedroulante.adapter.ListAdapter
import com.example.listedroulante.adapter.TaskAdapter
import com.example.listedroulante.utility.Task
import com.example.listedroulante.utility.TaskList
import com.example.listedroulante.utility.User
import com.example.listedroulante.utility.UserList

class TaskActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        var btnCreate: Button = findViewById(R.id.createTask)
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
        userList.listOfUser[0].listOfList[0].addTask(Task("Lire des livres","Sobriété heureuse", 0))
        userList.listOfUser[0].listOfList[0].addTask(Task("Manger","5 fruits et légumes", 0))
        userList.listOfUser[0].listOfList[0].addTask(Task("Dormir","Dans un lit", 0))
        userList.listOfUser[0].listOfList[0].addTask(Task("Survivre","Comme tu peux", 0))
        userList.listOfUser[0].listOfList[0].addTask(Task("Ecouter de la musique","French79", 0))
        userList.listOfUser[0].listOfList[0].addTask(Task("Dessiner","Inktober52", 0))
        userList.listOfUser[0].listOfList[0].addTask(Task("Chasser","Le bonheur", 0))
        userList.listOfUser[0].listOfList[0].addTask(Task("Voyager","Sobrement", 0))
        userList.listOfUser[0].listOfList[0].addTask(Task("Donner à bouffer à Titus","Des restes humain", 0))
        userList.listOfUser[0].listOfList[0].addTask(Task("Faire du sport","Faut pas déconner non plus", 0))
        userList.listOfUser[0].listOfList[0].addTask(Task("Aller aux putes","Natacha t'attendra", 0))

        var listTasks = userList.listOfUser[0].listOfList[0]

        //OnClickListener//
        fun listItemClicked (taskName : String) {
            Toast.makeText(this, "Clicked : $taskName" ,Toast.LENGTH_LONG).show()

        }

        //RecyclerView//
        val Task : RecyclerView = findViewById(R.id.rv_Task)
        val adapter = TaskAdapter(listTasks, { listName : String -> listItemClicked(listName) })
        Task.adapter = adapter
        Task.layoutManager = LinearLayoutManager(this)

    }

    fun alerter (texte : String) {
        Log.i ("CAT", texte)
        var t : Toast = Toast.makeText(this, texte, Toast.LENGTH_SHORT)
        t.show()
    }

    override fun onClick(v: View?) {
        var askName : EditText = findViewById(R.id.task)
        var name : String = askName.text.toString()
        when (v?.id) {
            R.id.createList -> {
                alerter("Task $name added")
            }
        }
    }
}