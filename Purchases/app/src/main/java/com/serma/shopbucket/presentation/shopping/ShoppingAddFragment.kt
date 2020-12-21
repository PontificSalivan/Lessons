package com.serma.shopbucket.presentation.shopping

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.serma.shopbucket.R
import com.serma.shopbucket.data.Failure
import com.serma.shopbucket.data.Success
import com.serma.shopbucket.di.factory.DaggerViewModelFactory
import com.serma.shopbucket.di.shopping.ShoppingComponent
import com.serma.shopbucket.domain.entity.Shopping
import com.serma.shopbucket.domain.showToast
import com.serma.shopbucket.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.bottom_sheet_add_purchase.*
import kotlinx.android.synthetic.main.fragment_add_shopping.*
import javax.inject.Inject

class ShoppingAddFragment : BaseFragment(R.layout.fragment_add_shopping) {

    @Inject
    lateinit var shoppingViewModelFactory: DaggerViewModelFactory
    private lateinit var viewModel: ShoppingViewModel

    private val purchaseId by lazy { arguments?.getString(SHOPPING_ADD_PURCHASE_ID) }
    private val shoppingId by lazy { arguments?.getString(SHOPPING_ADD_ID) }

    companion object {
        private const val SHOPPING_ADD_ID = "PURCHASE_ADD_ID"
        private const val SHOPPING_ADD_PURCHASE_ID = "SHOPPING_ADD_PURCHASE_ADD_ID"

        fun createBundle(idPurchase: String, idShopping: String? = null): Bundle {
            val bundle = Bundle()
            bundle.putString(SHOPPING_ADD_PURCHASE_ID, idPurchase)
            idShopping?.let {
                bundle.putString(SHOPPING_ADD_ID, idShopping)
            }
            return bundle
        }
    }

    override fun observeViewModel() {
        viewModel.response.observe(viewLifecycleOwner){
            when (it) {
                is Success -> {
                    val result = it.result
                    edtTitle.setText(result.name)
                    edtPrice.setText(result.price.toString())
                    edtCount.setText(result.count.toString())
                }
                is Failure -> showToast(R.string.error)
            }
        }
        viewModel.save.observe(viewLifecycleOwner) {
            when (it) {
                is Success -> {
                    findNavController().popBackStack()
                }
                is Failure -> showToast(R.string.error)
            }
        }
        viewModel.update.observe(viewLifecycleOwner) {
            when (it) {
                is Success -> {
                    showToast(R.string.change_complete)
                    findNavController().popBackStack()
                }
                is Failure -> showToast(R.string.error)
            }
        }
    }

    override fun initInputs(view: View) {
        btnSave.setOnClickListener {
            val name = edtTitle.text.toString()
            val count = edtCount.text.toString().toLong()
            val price = edtPrice.text.toString().toLong()
            purchaseId?.let { purchaseId ->
                if (shoppingId != null) {
                    viewModel.updateShopping(purchaseId, shoppingId!!, name, count, price)
                } else {
                    viewModel.saveShopping(purchaseId, name, count, price)
                }
            }
        }
    }

    override fun initView() {
        shoppingId?.let {
            viewModel.getShopping(purchaseId!!, it)
        }
    }

    override fun initDagger() {
        ShoppingComponent.init(requireActivity().application).inject(this)
        viewModel =
            ViewModelProvider(this, shoppingViewModelFactory)[ShoppingViewModel::class.java]
    }
}