package com.serma.shopbucket.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.serma.shopbucket.domain.entity.BaseFireStoreEntity

abstract class BaseAdapter<ViewHolder : BaseViewHolder<Type>, Type: BaseFireStoreEntity>(
    query: FirestoreRecyclerOptions<Type>
) :
    FirestoreRecyclerAdapter<Type, ViewHolder>(query) {

    var onClickListener: ((Type) -> Unit)? = null
    var onDeleteListener: ((Type?) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Type) {
        holder.onBind(model)
        holder.itemView.setOnClickListener { onClickListener?.invoke(model) }
    }
}

abstract class BaseViewHolder<Type>(itemView: View, val onDeleteListener: ((Type?) -> Unit)?) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(item: Type)
}