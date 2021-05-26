package com.sumauto.cube.log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sumauto.cube.R


class LogAdapter : RecyclerView.Adapter<LogAdapter.ViewHolder>() {
    private val values = mutableListOf<LogItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_log, parent, false)
        return ViewHolder(view)
    }

    fun addItem(text: String) {
        values.add(LogItem(text))
        notifyItemInserted(values.size - 1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvLog.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvLog: TextView = view.findViewById(R.id.tvLog)


    }
}