package com.serma.shopbucket.presentation.auth.login

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.serma.shopbucket.R
import com.serma.shopbucket.data.remote.contract.AuthState
import com.serma.shopbucket.di.auth.AuthComponent
import com.serma.shopbucket.di.auth.DaggerAuthComponent
import com.serma.shopbucket.di.factory.DaggerViewModelFactory
import com.serma.shopbucket.domain.showToast
import com.serma.shopbucket.presentation.auth.AuthValidationState
import com.serma.shopbucket.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    @Inject
    lateinit var loginViewModelFactory: DaggerViewModelFactory

    private lateinit var viewModel: LoginViewModel

    @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
    override fun observeViewModel() {
        viewModel.validationState.observe(viewLifecycleOwner) { state ->
            when (state) {
                AuthValidationState.INVALID_EMAIL -> showToast(R.string.invalid_email)
                AuthValidationState.INVALID_PASSWORD
                -> showToast(R.string.invalid_password)
                AuthValidationState.INVALID_EMAIL_AND_PASSWORD -> showToast(R.string.invalid_email_and_password)
                AuthValidationState.SUCCESS -> return@observe
            }
        }
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                AuthState.SUCCESS -> {
                    val navOptions =
                        NavOptions.Builder().setPopUpTo(R.id.purchaseListFragment, true).build()
                    findNavController().navigate(R.id.purchaseListFragment, null, navOptions)
                }
                AuthState.FAILURE -> showToast(R.string.server_error)
            }
        }
    }

    override fun initInputs(view: View) {
        loginBtn.setOnClickListener {
            val email = emailEdt.text.toString()
            val password = passwordEdt.text.toString()
            viewModel.login(email, password)
        }
        registration.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
    }

    override fun initView() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun initDagger() {
        AuthComponent.init(requireActivity().application).inject(this)
        viewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]
    }
}