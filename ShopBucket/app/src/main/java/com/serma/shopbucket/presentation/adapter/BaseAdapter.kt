package com.serma.shopbucket.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<ViewHolder : BaseViewHolder<Type>, Type> :
    RecyclerView.Adapter<ViewHolder>() {

    var items: List<Type>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClickListener: ((Int) -> Unit)? = null
    var onDeleteListener: ((Int) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.let {
            holder.onBind(items!![position], position)
            holder.itemView.setOnClickListener { onClickListener?.invoke(position) }
        }
    }

    override fun getItemCount() = items?.size ?: 0
}

abstract class BaseViewHolder<Type>(itemView: View, val onDeleteListener: ((Int) -> Unit)?) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(item: Type, pos: Int)
}