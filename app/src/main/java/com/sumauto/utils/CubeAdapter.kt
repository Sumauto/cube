package com.sumauto.utils

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sumauto.cube.R
import com.sumauto.cube.coroutines.CoroutinesActivity
import com.sumauto.cube.databinding.ListCubeItemBinding
import com.sumauto.cube.flow.WorkFlowTest
import com.sumauto.cube.rx.RxActivity
import com.sumauto.cube.shell.ShellActivity

class CubeAdapter : RecyclerView.Adapter<ViewBindingHolder<ListCubeItemBinding>>() {
    private val dataSet = listOf(
//        Item("222", CoroutinesActivity::class.java),
//        Item("222", ShellActivity::class.java),
//        Item("222", WorkFlowTest::class.java),
//        Item("222", RxActivity::class.java),
        Item("IPC Test", destination = R.id.navigate_ipc),
        Item("ANR Test", destination = R.id.navigate_anr)
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewBindingHolder<ListCubeItemBinding> {
        val binding =
            ListCubeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewBindingHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewBindingHolder<ListCubeItemBinding>, position: Int) {
        val listItem = dataSet[position]

        holder.binding.tvTitle.text = listItem.title
        holder.binding.tvDesc.text = listItem.desc
        holder.itemView.setOnClickListener {
            it.findNavController().navigate(listItem.destination)
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}


data class Item(
    val title: String,
    val desc: String = "",
    val destination: Int
)

