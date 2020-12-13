package com.serma.shopbucket.presentation.purchase

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.serma.shopbucket.R
import com.serma.shopbucket.ShopBucketApp
import com.serma.shopbucket.di.auth.DaggerAuthComponent
import com.serma.shopbucket.di.factory.DaggerViewModelFactory
import com.serma.shopbucket.domain.entity.Purchase
import com.serma.shopbucket.presentation.adapter.PurchaseAdapter
import com.serma.shopbucket.presentation.base.BaseFragment
import com.serma.shopbucket.presentation.shopping.ShoppingListFragment.Companion.PURCHASE_POS
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject


class PurchaseListFragment : BaseFragment(R.layout.fragment_list) {

    @Inject
    lateinit var adapter: PurchaseAdapter

    @Inject
    lateinit var purchaseViewModelFactory: DaggerViewModelFactory
    private lateinit var viewModel: PurchaseViewModel

    override fun observeViewModel() {
        viewModel.data.observe(viewLifecycleOwner) {
            adapter.items = it
        }
    }

    override fun initInputs(view: View) {
        recycleView.adapter = adapter
        btnAdd.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(PURCHASE_POS, -1)
            viewModel.addPurchase(Purchase())
            findNavController().navigate(R.id.shoppingListFragment, bundle) }
        adapter.onClickListener = {
            val bundle = Bundle()
            bundle.putInt("PURCHASE_POS", it + 1)
            findNavController().navigate(R.id.shoppingListFragment, bundle)
        }
        adapter.onDeleteListener = {
            viewModel.deletePurchase(it)
        }
    }

    override fun initDagger() {
            (requireActivity().application as ShopBucketApp).getAppComponent().inject(this)
        viewModel =
            ViewModelProvider(this, purchaseViewModelFactory)[PurchaseViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        viewModel.getData()
    }
}