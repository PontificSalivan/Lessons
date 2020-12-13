package com.serma.shopbucket.presentation.shopping

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.serma.shopbucket.R
import com.serma.shopbucket.ShopBucketApp
import com.serma.shopbucket.di.auth.DaggerAuthComponent
import com.serma.shopbucket.di.factory.DaggerViewModelFactory
import com.serma.shopbucket.domain.entity.Shopping
import com.serma.shopbucket.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_shopping.*
import javax.inject.Inject

class ShoppingAddFragment : BaseFragment(R.layout.fragment_add_shopping) {

    @Inject
    lateinit var shoppingViewModelFactory: DaggerViewModelFactory
    private lateinit var viewModel: ShoppingViewModel
    private var pos: Int = -1

    override fun observeViewModel() {
    }

    override fun initInputs(view: View) {
        btnSave.setOnClickListener {
            val shopping = Shopping(
                edtTitle.text.toString(),
                edtCount.text.toString().toInt(),
                edtPrice.text.toString().toInt()
            )
            viewModel.addShopping(pos, shopping)
            viewModel.updatePurchaseWithShopping(pos, shopping)
            findNavController().popBackStack()
        }
    }

    override fun initView() {
        pos = arguments?.getInt(ShoppingListFragment.PURCHASE_POS, -1) ?: -1
        if (pos == -1) {
            pos = viewModel.getPurchasePos()
        }
    }

    override fun initDagger() {
        (requireActivity().application as ShopBucketApp).getAppComponent().inject(this)
        viewModel =
            ViewModelProvider(this, shoppingViewModelFactory)[ShoppingViewModel::class.java]
    }
}