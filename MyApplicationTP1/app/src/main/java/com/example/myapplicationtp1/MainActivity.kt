package com.example.myapplicationtp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText

class MainActivity : ToolbarActivity() {
    companion object {
        var pseudo = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    fun submitPseudo(view: View){
        //val intent = Intent(this,ChoixListActivity::class.java)
        //startActivity(intent )
        pseudo = findViewById<EditText>(R.id.pseudo).text.toString()
        Log.v("Pseudo = ",pseudo)
        val intent = Intent(this, ChoixListActivity::class.java)
        intent.putExtra("Pseudo",pseudo)
        startActivity(intent)
    }
}