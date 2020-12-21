package com.serma.shopbucket.presentation.purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.serma.shopbucket.R
import com.serma.shopbucket.data.Failure
import com.serma.shopbucket.data.Success
import com.serma.shopbucket.di.factory.DaggerViewModelFactory
import com.serma.shopbucket.di.purchase.PurchaseComponent
import com.serma.shopbucket.domain.showToast
import kotlinx.android.synthetic.main.bottom_sheet_add_purchase.*
import javax.inject.Inject

class PurchaseAddFragment : BottomSheetDialogFragment() {

    companion object {

        const val PURCHASE_ADD_ID = "PURCHASE_ADD_ID"

        fun createBundle(id: String): Bundle {
            val bundle = Bundle()
            bundle.putString(PURCHASE_ADD_ID, id)
            return bundle
        }
    }

    @Inject
    lateinit var purchaseViewModelFactory: DaggerViewModelFactory
    private lateinit var viewModel: PurchaseViewModel

    private val id by lazy { arguments?.getString(PURCHASE_ADD_ID) }

    private fun observeViewModel() {
        viewModel.response.observe(viewLifecycleOwner) {
            when (it) {
                is Success -> {
                    edtName.setText(it.result.name)
                }
                is Failure -> showToast(R.string.error)
            }
        }
        viewModel.save.observe(viewLifecycleOwner) {
            when (it) {
                is Success -> {
                    dismiss()
                }
                is Failure -> showToast(R.string.error)
            }
        }
        viewModel.update.observe(viewLifecycleOwner) {
            when (it) {
                is Success -> {
                    showToast(R.string.change_complete)
                    dismiss()
                }
                is Failure -> showToast(R.string.error)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_purchase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDagger()
        observeViewModel()
        id?.let {
            viewModel.getPurchase(it)
        }
        saveBtn.setOnClickListener {
            if (id != null) {
                viewModel.updatePurchase(id!!, edtName.text.toString())
            } else {
                viewModel.savePurchase(edtName.text.toString())
            }
        }
    }

    private fun initDagger() {
        PurchaseComponent.init(requireActivity().application).inject(this)
        viewModel =
            ViewModelProvider(this, purchaseViewModelFactory)[PurchaseViewModel::class.java]
    }
}