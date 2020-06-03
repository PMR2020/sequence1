package com.example.listedroulante.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.listedroulante.R
import com.example.listedroulante.utility.User
import com.example.listedroulante.utility.jsonManager
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // l'écran UI à afficher

        //Listener//
        var btnCreate: Button = findViewById(R.id.Create)
        btnCreate.setOnClickListener(this)
    }
        //btnCreate.setOnClickListener(View.OnClickListener() {
        //    fun onClick(v : View) {
        //    alerter ("click on create")
        //    }
        //}

        //gestion du menu//
    override fun onCreateOptionsMenu(menu : Menu?): Boolean {
            getMenuInflater().inflate(R.menu.menu, menu)
            return true
        }

    //menu//
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.getItemId()
        when (id) {
            R.id.preferences -> alerter("Click on pref")
        }
        return super.onOptionsItemSelected(item)
    }

        //Gestion des task //

    val filePath : String = "userlist.json"
    val file = createFile(filePath)
    val userList = jsonManager.fromFileToUserList(file)

    override fun onClick(v: View?) {
        var askPseudo : EditText = findViewById(R.id.entree_pseudo)
        var pseudo : String = askPseudo.text.toString()

        when (v?.id) {
            R.id.Create -> {
                userList?.listOfUser?.add(User(pseudo))
                jsonManager.fromUserListToFile(userList, file, this)
                alerter("$pseudo user created")
                val intent = Intent (this, ChoiceActivity::class.java)
                startActivity(intent)
                }

        }
    }

    // Toasts //
    //fun foo (view : View) {
    //    alerter("User created")
    //}
    fun fooText (view : View) {
        alerter("Enter your name")
    }

    fun alerter (texte : String) {
        Log.i ("CAT", texte)
        var t : Toast = Toast.makeText(this, texte, Toast.LENGTH_SHORT)
        t.show()
    }

    fun createFile (fileName : String): File? {
        try {
            return File(fileName)
        } catch (e : FileAlreadyExistsException) {
            e.printStackTrace()
            return null
        }
    }
}


