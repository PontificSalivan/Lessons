package com.serma.shopbucket.presentation.shopping

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
import com.serma.shopbucket.presentation.adapter.ShoppingAdapter
import com.serma.shopbucket.presentation.base.BaseFragment
import com.serma.shopbucket.presentation.purchase.PurchaseViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class ShoppingListFragment : BaseFragment(R.layout.fragment_shopping_list) {

    @Inject
    lateinit var adapter: ShoppingAdapter

    @Inject
    lateinit var shoppingViewModelFactory: DaggerViewModelFactory
    private lateinit var viewModel: ShoppingViewModel
    private var pos: Int = -1

    companion object {
        const val PURCHASE_POS = "PURCHASE_POS"
    }

    override fun observeViewModel() {
        viewModel.data.observe(viewLifecycleOwner) {
            adapter.items = it
        }
    }

    override fun initInputs(view: View) {
        recycleView.adapter = adapter
        adapter.onClickListener = {
            val bundle = Bundle()
            bundle.putInt("PURCHASE_POS", it + 1)
            findNavController().navigate(R.id.shoppingAddFragment, bundle)
        }
        btnAdd.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("PURCHASE_POS", pos)
            findNavController().navigate(R.id.shoppingAddFragment, bundle)
        }
        adapter.onDeleteListener = {
            viewModel.deleteShopping(pos, it)
        }
    }

    override fun initView() {
        pos = arguments?.getInt(PURCHASE_POS, -1) ?: -1
        if (pos == -1) {
            pos = viewModel.getPurchasePos()
        }
    }

    override fun initDagger() {
        (requireActivity().application as ShopBucketApp).getAppComponent().inject(this)
        viewModel =
            ViewModelProvider(this, shoppingViewModelFactory)[ShoppingViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        viewModel.getData(pos)
    }
}