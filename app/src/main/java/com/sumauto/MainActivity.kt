package com.sumauto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.sumauto.cube.R
import com.sumauto.cube.coroutines.CoroutinesActivity
import com.sumauto.cube.shell.ShellActivity
import com.sumauto.cube.usage.UsageHelp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = MyAdapter()
        UsageHelp.query(this)
    }

    private inner class MyAdapter : RecyclerView.Adapter<MyHolder>() {
        val dataSet = listOf(
                Item("222", CoroutinesActivity::class.java),
                Item("222", ShellActivity::class.java),
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val v = layoutInflater.inflate(android.R.layout.simple_list_item_2, parent, false)
            return MyHolder(v)
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            val listItem = dataSet[position]
            holder.textView1.text = listItem.title
            holder.textView2.text = listItem.activityCls.name
            holder.itemView.setOnClickListener {
                startActivity(Intent(it.context, listItem.activityCls))
            }

        }

        override fun getItemCount(): Int {
            return dataSet.size
        }

    }

    private class MyHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textView1: TextView = v.findViewById(android.R.id.text1)
        val textView2: TextView = v.findViewById(android.R.id.text2)
    }

    data class Item(
            val title: String,
            val activityCls: Class<out Activity>
    )
}