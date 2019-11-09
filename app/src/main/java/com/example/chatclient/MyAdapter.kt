package com.example.chatclient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_line.view.*
import kotlin.collections.ArrayList

/**
 * Very basic adapter for recycleView-element
 */

class MyAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.message_line, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.msg.text = items[pos]
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val msg = view.message

}