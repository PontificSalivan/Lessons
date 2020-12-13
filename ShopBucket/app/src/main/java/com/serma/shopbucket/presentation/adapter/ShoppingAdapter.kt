package com.serma.shopbucket.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.serma.shopbucket.R
import com.serma.shopbucket.domain.entity.Shopping
import kotlinx.android.synthetic.main.item_list_shopping.view.*
import kotlinx.android.synthetic.main.item_list_shopping.view.btnDelete
import kotlinx.android.synthetic.main.item_list_shopping.view.title
import javax.inject.Inject

class ShoppingAdapter @Inject constructor() : BaseAdapter<ShoppingViewHolder, Shopping>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_shopping, parent, false)
        return ShoppingViewHolder(view, onDeleteListener)
    }
}

class ShoppingViewHolder(itemView: View, onDeleteListener: ((Int) -> Unit)?) :
    BaseViewHolder<Shopping>(itemView, onDeleteListener) {

    @SuppressLint("SetTextI18n")
    override fun onBind(item: Shopping, pos: Int) {
        itemView.title.text = item.name
        itemView.count.text = item.count.toString()
        itemView.price.text = item.price.toString()
        itemView.btnDelete.setOnClickListener { onDeleteListener?.invoke(pos) }
    }
}