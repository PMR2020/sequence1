package com.example.tp_boukadir_v2

import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

open class GenericActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.preferences -> {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)

            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }


    }

    override fun onClick(v: View) {
    }
}