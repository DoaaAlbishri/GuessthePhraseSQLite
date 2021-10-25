package com.example.guessthephrasesqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHlr(context: Context?) : SQLiteOpenHelper(context, "details.db", null, 1) {
    val sql :SQLiteDatabase = writableDatabase

    override fun onCreate(p0: SQLiteDatabase?) {
        if (p0 != null) {
            p0.execSQL("create table phrase(Phrase text)")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun savedata(s1: String){
        val cv = ContentValues()
        cv.put("Phrase", s1)
        sql.insert("phrase", null, cv)
    }

    @SuppressLint("Range")
    fun retrieve() :ArrayList<String> {
        val list = ArrayList<String>()
        val cursor: Cursor = sql.rawQuery("select * from phrase", null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                val name: String = cursor.getString(cursor.getColumnIndex("Phrase"))
                list.add(name)
                cursor.moveToNext()
            }
        }
        return list
    }
}

/*
    @SuppressLint("Range")
    fun retrive(s1:String) :String {
        var c :Cursor = sql.query("notes", null, "Note=?", arrayOf(s1), null, null, null)
        c.moveToFirst()
        var note = c.getString(c.getColumnIndex("Note"))
        return note
        }
 */