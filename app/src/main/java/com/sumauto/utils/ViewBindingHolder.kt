package com.sumauto.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class ViewBindingHolder<VB : ViewBinding>(var binding: VB) : RecyclerView.ViewHolder(binding.root) {

}