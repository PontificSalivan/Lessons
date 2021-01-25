package com.serma.shopbucket.presentation.purchase

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
import com.serma.shopbucket.di.purchase.PurchaseComponent
import com.serma.shopbucket.domain.showToast
import com.serma.shopbucket.presentation.adapter.PurchaseAdapter
import com.serma.shopbucket.presentation.base.BaseFragment
import com.serma.shopbucket.presentation.shopping.ShoppingListFragment
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject


class PurchaseListFragment : BaseFragment(R.layout.fragment_list) {

    @Inject
    lateinit var adapter: PurchaseAdapter

    @Inject
    lateinit var purchaseViewModelFactory: DaggerViewModelFactory
    private lateinit var viewModel: PurchaseViewModel

    private var showText = false

    override fun observeViewModel() {
        viewModel.delete.observe(viewLifecycleOwner) {
            when (it) {
                is Success -> if (showText) showToast(R.string.delete_complete)
                is Failure -> showToast(R.string.error)
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
    }

    override fun initInputs(view: View) {
        recycleView.adapter = adapter
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.purchaseAddFragment)
        }
        adapter.onClickListener = { item ->
            item.id?.let {
                val bundle = ShoppingListFragment.createBundle(it)
                findNavController().navigate(R.id.shoppingListFragment, bundle)
            }
        }
        adapter.onDeleteListener = { item ->
            item?.id?.let {
                viewModel.deletePurchase(it)
                showText = true
            }
        }
        adapter.onEditListener = { item ->
            item.id?.let {
                val bundle = PurchaseAddFragment.createBundle(it)
                findNavController().navigate(R.id.purchaseAddFragment, bundle)
            }
        }
    }

    override fun initDagger() {
        PurchaseComponent.init(requireActivity().application).inject(this)
        viewModel =
            ViewModelProvider(this, purchaseViewModelFactory)[PurchaseViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
        showText = false
    }
}