package com.example.tp_boukadir_v2

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.preference.PreferenceManager
import java.io.File

class MainActivity : GenericActivity()  {

    private var refBtn: Button? = null
    private var refPseudoInput: EditText? = null
    private var prefs : SharedPreferences ?= null
    private var filename : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refBtn = findViewById(R.id.BtnMain)
        refPseudoInput = findViewById(R.id.pseudoInput)
        refBtn?.let { btn -> btn.setOnClickListener(this)}
        filename = "players"
        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val file = File(filesDir, filename)
        if (!file.exists()) {
            file.createNewFile()
        }


    }

     override fun onClick(v: View) {
        when (v.id){
            R.id.BtnMain -> {
                val pseudo = refPseudoInput!!.text.toString()

                val editor : SharedPreferences.Editor = prefs!!.edit()
                editor.putString("pseudo", pseudo)
                editor.apply()

                val bundle = Bundle()
                bundle.putString("pseudo", pseudo)
                val intent = Intent(this, ChoixList::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }


}
