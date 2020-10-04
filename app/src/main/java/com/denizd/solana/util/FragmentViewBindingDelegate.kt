package com.denizd.solana.util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * This class is used for creating a read-only ViewBinding property
 * for a fragment that observes its lifecycle and cleans itself
 * up accordingly.
 *
 * @author  Zhuinden
 */
class FragmentViewBindingDelegate<T : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T> {

    /*
     * ViewBinding property
     */
    private var _binding: T? = null

    /**
     * Adds an observer to the fragment lifecycle to destroy the
     * binding object if appropriate.
     */
    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            _binding = null
                        }
                    })
                }
            }
        })
    }

    /**
     * Used for getting the ViewBinding property. Fails if the parent Fragment
     * is already destroyed.
     *
     * @param   thisRef fragment to which the view is bound to
     * @param   property unused
     * @return  ViewBinding property
     * @throws  IllegalStateException if the parent Fragment is not ready
     *          (not yet initialised or already destroyed)
     */
    @Throws(IllegalStateException::class)
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val binding = _binding
        if (binding != null) {
            return binding
        }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException(
                "Should not attempt to get bindings when Fragment views are destroyed."
            )
        }

        return viewBindingFactory(thisRef.requireView()).also { _binding = it }
    }
}
/**
 * Extension function for creating a ViewBinding object for a Fragment
 *
 * @param   viewBindingFactory function that binds a view to a ViewBinding property
 * @return  a read-only property that implements ViewBinding
 */
fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)
