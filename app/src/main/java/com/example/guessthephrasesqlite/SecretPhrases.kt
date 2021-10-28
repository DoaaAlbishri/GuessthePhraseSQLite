package com.example.guessthephrasesqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class SecretPhrases : AppCompatActivity() {
    lateinit var edphrase : EditText
    lateinit var btnAdd : Button
    //db
    lateinit var dbhlr :DBHlr
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret_phrases)
        dbhlr = DBHlr(this)
        edphrase = findViewById(R.id.edphrase)
        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            if(edphrase.text.isEmpty())
                Toast.makeText(this, "Fill the fields please", Toast.LENGTH_SHORT).show()
            else {
                dbhlr.savedata(edphrase.text.toString())
                println(edphrase.text.toString())
                edphrase.text.clear()
                Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show()
            }
        }
        //add some phrase
        //var phraseList = arrayListOf<String>("hello world","I am developer","I love Kotlin")
        //for (i in phraseList) {
        //    println(i)
        //}
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menus, menu)
        val item1: MenuItem = menu!!.getItem(1)
        item1.setVisible(false)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.m1 -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}