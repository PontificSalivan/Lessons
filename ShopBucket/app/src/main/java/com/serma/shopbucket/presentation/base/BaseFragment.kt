package com.serma.shopbucket.presentation.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment(layout: Int) : Fragment(layout) {

    abstract fun observeViewModel()
    abstract fun initInputs(view: View)
    abstract fun initDagger()
    open protected fun initView(){}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDagger()
        initInputs(view)
        observeViewModel()
        initView()
    }

}