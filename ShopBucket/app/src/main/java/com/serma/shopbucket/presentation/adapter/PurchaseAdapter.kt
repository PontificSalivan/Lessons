package com.serma.shopbucket.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.serma.shopbucket.R
import com.serma.shopbucket.domain.entity.Purchase
import kotlinx.android.synthetic.main.item_list_purchase.view.*
import javax.inject.Inject

class PurchaseAdapter @Inject constructor() : BaseAdapter<PurchaseViewHolder, Purchase>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_purchase, parent, false)
        return PurchaseViewHolder(view, onDeleteListener)
    }
}

class PurchaseViewHolder(itemView: View, onDeleteListener: ((Int) -> Unit)?) :
    BaseViewHolder<Purchase>(itemView, onDeleteListener) {

    @SuppressLint("SetTextI18n")
    override fun onBind(item: Purchase, pos: Int) {
        itemView.title.text = item.name
        itemView.itemCount.text = "${item.progress}/${item.total}"
        itemView.progressBar.max = item.total
        itemView.progressBar.progress = item.progress
        itemView.btnDelete.setOnClickListener { onDeleteListener?.invoke(pos) }
    }
}