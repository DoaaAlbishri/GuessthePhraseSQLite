package com.example.guessthephrasesqlite

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var editText : EditText
    lateinit var button : Button
    lateinit var myLayout : ConstraintLayout
    lateinit var myRv : RecyclerView
    lateinit var tv1 : TextView
    //db
    lateinit var dbhlr :DBHlr

    var pharse = "Hello World"
    var str :String =""
    var Newstr = str
    var left = 10
    var letter = 10
    var guessList = arrayListOf<String>()
    var leftList = arrayListOf<String>()
    var letterList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myLayout = findViewById(R.id.clMain)
        tv1 = findViewById(R.id.tv)
        myRv = findViewById(R.id.recyclerView)
        button = findViewById(R.id.button)
        editText = findViewById(R.id.editText)
        //db
        dbhlr = DBHlr(this)
        //retrieve and select the phrase
        var list = dbhlr.retrieve()
        println(list.size)
        println(list)
        pharse = list[Random.nextInt(list.size-1)]
        println(pharse)
        str = "*".repeat(pharse.length)
        Newstr = str
        tv1.setText("Pharse: ${str} \n Gussed Letter: ")

        myRv.adapter = RecyclerViewAdapter(guessList, leftList, letterList)
        myRv.layoutManager = LinearLayoutManager(this)

        button.setOnClickListener {
            pharse()
            editText.text.clear()
        }
    }

    private fun pharse() {
        if (left > 0) {
            var input = editText.text.toString()
            if (!editText.text.isEmpty()) {
                if (pharse.toLowerCase() == input.toLowerCase()) {
                    --left
                    tv1.setText("Pharse: ${input} \n Gussed Letter: ")
                    guessList.add("")
                    letterList.add("")
                    leftList.add("${left} guesses remaining")
                    myRv.adapter?.notifyDataSetChanged()
                    myRv.smoothScrollToPosition(guessList.size - 1)
                    winDialog()
                } else {
                    --left
                    guessList.add("Wrong guess : ${input}")
                    leftList.add("${left} guesses remaining")
                    letterList.add("")
                    myRv.adapter?.notifyDataSetChanged()
                    myRv.smoothScrollToPosition(guessList.size - 1)
                }
                if (left == 0) {
                    editText.hint = "Gusses the letter"
                }
            }
        } else {
            letter()
        }
    }

    private fun letter(){
        editText.hint = "Gusses the letter"
        var input: Char = editText.text.toString()[0]
        var count = 0
        if (!editText.text.isEmpty() && editText.text.length == 1) {
            letter--
            for (i in 0..pharse.length - 1) {
                if (pharse[i].toLowerCase().equals(input.toLowerCase())) {
                    var chars = Newstr.toCharArray()
                    chars[i] = input
                    Newstr = String(chars)
                    // Newstr = Newstr.replace(Newstr[i], input)
                    count++
                }
                if (pharse[i] == ' ') {
                    var chars = Newstr.toCharArray()
                    chars[i] = ' '
                    Newstr = String(chars)
                }
            }
            if (count > 0) {
                tv1.setText("Pharse: ${Newstr} \n Gussed Letter: ${input} ")
                guessList.add("Wrong guess : ")
                letterList.add("Founds: ${count} ${input.toUpperCase()}(s)")
                leftList.add("${letter} guesses remaining")
                myRv.adapter?.notifyDataSetChanged()
                myRv.smoothScrollToPosition(guessList.size - 1)
            } else {
                guessList.add("Wrong guess : ${input}")
                letterList.add("")
                leftList.add("${letter} guesses remaining")
                myRv.adapter?.notifyDataSetChanged()
                myRv.smoothScrollToPosition(guessList.size - 1)
            }
        } else {
            Snackbar.make(myLayout, "Enter one letter please", Snackbar.LENGTH_LONG).show()
        }
        if (Newstr.equals(pharse, true)){
            winDialog()
        }
        if(letter == 0 && !Newstr.equals(pharse, true)){
                gameoverDialog()
        }
    }

    private fun winDialog(){
        // first we create a variable to hold an AlertDialog builder
        val dialogBuilder = AlertDialog.Builder(this)
        // here we set the message of our alert dialog
        dialogBuilder.setMessage("Congratulation The phrase is : $pharse")

                // positive button text and action
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                    this.recreate()
                })
        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Winner")
        // show alert dialog
        alert.show()
    }

    private fun gameoverDialog(){
        // first we create a variable to hold an AlertDialog builder
        val dialogBuilder = AlertDialog.Builder(this)
        // here we set the message of our alert dialog
        dialogBuilder.setMessage("Game Over")

                // positive button text and action
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                    this.recreate()
                })
        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Game Over")
        // show alert dialog
        alert.show()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menus, menu)
        val item1: MenuItem = menu!!.getItem(0)
        item1.setVisible(false)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.m2 -> {
                val intent = Intent(this, SecretPhrases::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}