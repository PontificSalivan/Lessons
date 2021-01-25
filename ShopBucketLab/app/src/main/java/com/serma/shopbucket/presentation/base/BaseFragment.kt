package com.serma.shopbucket.presentation.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.serma.shopbucket.data.remote.contract.NoAction

abstract class BaseFragment(layout: Int) : Fragment(layout) {

    abstract fun observeViewModel()
    abstract fun initInputs(view: View)
    abstract fun initDagger()
    protected open fun initView() = NoAction

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDagger()
        initView()
        initInputs(view)
        observeViewModel()
    }

}