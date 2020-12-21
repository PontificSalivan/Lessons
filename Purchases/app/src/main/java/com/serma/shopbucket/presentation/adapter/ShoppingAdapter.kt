package com.serma.shopbucket.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.serma.shopbucket.R
import com.serma.shopbucket.domain.entity.Shopping
import kotlinx.android.synthetic.main.item_list_shopping.view.*
import javax.inject.Inject

class ShoppingAdapter @Inject constructor(query: FirestoreRecyclerOptions<Shopping>) :
    BaseAdapter<ShoppingViewHolder, Shopping>(query) {

    var onCompleteListener: ((Boolean, Shopping) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_shopping, parent, false)
        return ShoppingViewHolder(view, onDeleteListener, onCompleteListener)
    }
}

class ShoppingViewHolder(
    itemView: View,
    onDeleteListener: ((Shopping?) -> Unit)?,
    private val onCompleteListener: ((Boolean, Shopping) -> Unit)?
) :
    BaseViewHolder<Shopping>(itemView, onDeleteListener) {

    @SuppressLint("SetTextI18n")
    override fun onBind(item: Shopping) {
        itemView.title.text = item.name
        itemView.count.text = item.count.toString()
        itemView.price.text = item.price.toString()
        itemView.btnDelete.setOnClickListener { onDeleteListener?.invoke(item) }
        itemView.btnComplete.isChecked = item.complete
        itemView.btnComplete.setOnClickListener {
            onCompleteListener?.invoke(
                !item.complete,
                item
            )
        }
    }
}