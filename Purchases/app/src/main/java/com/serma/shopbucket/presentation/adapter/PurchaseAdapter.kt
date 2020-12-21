package com.serma.shopbucket.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.serma.shopbucket.R
import com.serma.shopbucket.domain.entity.Purchase
import kotlinx.android.synthetic.main.item_list_purchase.view.*
import javax.inject.Inject

class PurchaseAdapter @Inject constructor(query: FirestoreRecyclerOptions<Purchase>) :
    BaseAdapter<PurchaseViewHolder, Purchase>(query) {

    var onEditListener: ((Purchase) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_purchase, parent, false)
        return PurchaseViewHolder(view, onDeleteListener, onEditListener)
    }
}

class PurchaseViewHolder(
    itemView: View,
    onDeleteListener: ((Purchase?) -> Unit)?,
    private val onEditListener: ((Purchase) -> Unit)?
) :
    BaseViewHolder<Purchase>(itemView, onDeleteListener) {

    @SuppressLint("SetTextI18n")
    override fun onBind(item: Purchase) {
        itemView.title.text = item.name
        itemView.itemCount.text = "${item.progress}/${item.total}"
        itemView.progressBar.max = item.total.toInt()
        itemView.progressBar.progress = item.progress.toInt()
        itemView.btnDelete.setOnClickListener { onDeleteListener?.invoke(item) }
        itemView.btnEdit.setOnClickListener { onEditListener?.invoke(item) }
    }
}