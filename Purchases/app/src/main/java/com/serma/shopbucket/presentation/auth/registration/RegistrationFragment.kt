package com.serma.shopbucket.presentation.auth.registration

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.serma.shopbucket.R
import com.serma.shopbucket.ShopBucketApp
import com.serma.shopbucket.data.remote.AuthState
import com.serma.shopbucket.di.factory.DaggerViewModelFactory
import com.serma.shopbucket.presentation.auth.AuthValidationState
import com.serma.shopbucket.presentation.base.BaseFragment
import com.serma.shopbucket.presentation.mediator.RegistrationMediator
import kotlinx.android.synthetic.main.fragment_login.emailEdt
import kotlinx.android.synthetic.main.fragment_login.loginBtn
import kotlinx.android.synthetic.main.fragment_login.passwordEdt
import kotlinx.android.synthetic.main.fragment_registration.*
import javax.inject.Inject

class RegistrationFragment : BaseFragment(R.layout.fragment_registration) {

    @Inject
    lateinit var registrationMediator: RegistrationMediator

    @Inject
    lateinit var registrationViewModelFactory: DaggerViewModelFactory
    private lateinit var viewModel: RegistrationViewModel

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
        viewModel.validationNicknameState.observe(viewLifecycleOwner) { state ->
            when (state) {
                NicknameValidationState.SUCCESS -> register()
                NicknameValidationState.FAILURE_NICKNAME_ALREADY_EXIST -> showToast(R.string.nickname_already_exist)
                NicknameValidationState.FAILURE -> showToast(R.string.server_error)
                NicknameValidationState.FAILURE_EMPTY -> showToast(R.string.nickname_empty)
            }
        }
        viewModel.registrationState.observe(viewLifecycleOwner) { state ->
            when (state) {
                AuthState.SUCCESS -> showToast(R.string.registration)
                AuthState.FAILURE -> showToast(R.string.server_error)
            }
        }
    }

    override fun initInputs(view: View) {
        registrationBtn.setOnClickListener {
            viewModel.registration()
        }
        loginBtn.setOnClickListener {
            registrationMediator.openLoginScreen()
        }
    }

    private fun register() {
        val email = emailEdt.text.toString()
        val password = passwordEdt.text.toString()
        viewModel.registration(email, password)
    }

    override fun initDagger() {
        DaggerAuthComponent.factory().create(
            findNavController(),
            (requireActivity().application as ShopBucketApp).getAppComponent()
        ).inject(this)
        viewModel =
            ViewModelProvider(this, registrationViewModelFactory)[RegistrationViewModel::class.java]
    }

}