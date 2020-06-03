package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AbstractActivity(){

    companion object {
        const val userPref : String = "userPreferences"
        const val userPseudoKey: String = "userPseudo"
        const val defaultPseudoValue = "none"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clickListener = View.OnClickListener { view ->
            when(view.id){
                R.id.btnOk -> onClickBtnOk()
            }
        }
        val btnOk = findViewById<Button>(R.id.btnOk)
        btnOk.setOnClickListener(clickListener)


    }


    private fun onClickBtnOk(){
        val intent = Intent(this, ToDoListActivity::class.java)
        val txtUserPseudo = findViewById<TextView>(R.id.edtPseudo)

        /*
        val b = Bundle()
        b.putString("userPseudo", txtUserPseudo.text.toString())
        intent.putExtras(b)*/

        val sharedPreferences = getSharedPreferences(userPref, Context.MODE_PRIVATE) //on ustilise des sharedPreferences poutr gerer l'utilisateur tout au long du programme
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(userPseudoKey, txtUserPseudo.text.toString())
        editor.apply()
        startActivity(intent)
    }
}
