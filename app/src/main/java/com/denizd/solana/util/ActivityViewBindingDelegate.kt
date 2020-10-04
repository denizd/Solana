package com.denizd.solana.util

import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding

/**
 * Extension function for creating a ViewBinding object for an activity
 *
 * @author  Zhuinden
 * @param   bindingInflater function that inflates a view into a ViewBinding property
 * @return  ViewBinding property
 */
inline fun <T : ViewBinding> FragmentActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }
