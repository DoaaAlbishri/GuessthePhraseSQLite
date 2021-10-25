package com.example.guessthephrasesqlite

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_row.view.*
import kotlinx.android.synthetic.main.item_row.view.tv
import kotlinx.android.synthetic.main.item_row.view.tv1
import kotlin.random.Random

class RecyclerViewAdapter(private val guesses: ArrayList<String>,private val lefts: ArrayList<String>,private val letters: ArrayList<String>) : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>(){
    class ItemViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_row
                        ,parent
                        ,false
                )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val guess=guesses[position]
        val left=lefts[position]
        val letter= letters[position]
        holder.itemView.apply{
            tv.text=guess
            tv1.text=letter
            tv2.text=left
        }
    }

    override fun getItemCount(): Int = guesses.size

}