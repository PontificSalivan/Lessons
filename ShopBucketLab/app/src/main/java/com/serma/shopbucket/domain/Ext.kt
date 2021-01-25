package com.serma.shopbucket.domain

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(textId: Int) {
    Toast.makeText(requireContext(), getString(textId), Toast.LENGTH_SHORT).show()
}