package com.serma.shopbucket.presentation.shopping

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.serma.shopbucket.R
import com.serma.shopbucket.data.Failure
import com.serma.shopbucket.data.Success
import com.serma.shopbucket.di.factory.DaggerViewModelFactory
import com.serma.shopbucket.di.shopping.ShoppingComponent
import com.serma.shopbucket.domain.showToast
import com.serma.shopbucket.presentation.adapter.ShoppingAdapter
import com.serma.shopbucket.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_shopping_list.*
import javax.inject.Inject

class ShoppingListFragment : BaseFragment(R.layout.fragment_shopping_list) {

    private var adapter: ShoppingAdapter? = null

    @Inject
    lateinit var shoppingViewModelFactory: DaggerViewModelFactory
    private lateinit var viewModel: ShoppingViewModel

    private val id by lazy { arguments?.getString(SHOPPING_LIST_PURCHASE_ID) }

    companion object {
        const val SHOPPING_LIST_PURCHASE_ID = "SHOPPING_LIST_PURCHASE_ID"

        fun createBundle(id: String): Bundle {
            val bundle = Bundle()
            bundle.putString(SHOPPING_LIST_PURCHASE_ID, id)
            return bundle
        }
    }

    override fun observeViewModel() {
        viewModel.delete.observe(viewLifecycleOwner) {
            when (it) {
                is Success -> showToast(R.string.delete_complete)
                is Failure -> showToast(R.string.error)
            }
        }
        viewModel.purchase.observe(viewLifecycleOwner) {
            when (it) {
                is Success -> tvAllPrice.text = it.result.totalPrice.toString()
                is Failure -> showToast(R.string.error)
            }
        }
    }

    override fun initInputs(view: View) {
        id?.let { purchaseId ->
            adapter?.onClickListener = { shopping ->
                val bundle = ShoppingAddFragment.createBundle(purchaseId, shopping.id)
                findNavController().navigate(R.id.shoppingAddFragment, bundle)
            }
            btnAdd.setOnClickListener {
                val bundle = ShoppingAddFragment.createBundle(purchaseId)
                findNavController().navigate(R.id.shoppingAddFragment, bundle)
            }
            adapter?.onDeleteListener = { item ->
                item?.let {
                    viewModel.deleteShopping(
                        purchaseId,
                        item.id!!,
                        item.price,
                        item.count,
                        item.complete
                    )
                    viewModel.updateTotalPrice(purchaseId)
                }
            }
            adapter?.onCompleteListener = { isChecked, item ->
                viewModel.updateShopping(purchaseId, item.id!!, isChecked)
            }
        }
    }

    override fun initView() {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        val dividerItemDecoration = DividerItemDecoration(
            recycleView.context,
            LinearLayoutManager.VERTICAL
        )
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.list_item_divider)
        drawable?.let {
            dividerItemDecoration.setDrawable(it)
        }
        recycleView.addItemDecoration(dividerItemDecoration)
        id?.let {
            adapter = ShoppingAdapter(viewModel.getQuery(it))
            viewModel.updateTotalPrice(it)
        }
        recycleView.adapter = adapter
    }

    override fun initDagger() {
        ShoppingComponent.init(requireActivity().application).inject(this)
        viewModel =
            ViewModelProvider(this, shoppingViewModelFactory)[ShoppingViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }
}