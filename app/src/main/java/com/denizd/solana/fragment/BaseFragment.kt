package com.denizd.solana.fragment

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.denizd.solana.R
import com.denizd.solana.activity.MainActivity

/**
 * The base class for every fragment. This overrides certain methods, like
 * getContext(), to return a non-null context for easier handling.
 *
 * Provides abstract `binding` and `viewModel` properties to ensure that
 * every Fragment has its appropriate properties initialised.
 */
abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    /*
     * Non-null Context object that is initialised in onAttach()
     * and accessed through getContext() or property syntax `context`.
     */
    private lateinit var _context: Context

    /*
     * ViewBinding property used for safely accessing a view's elements.
     */
    protected abstract val binding: ViewBinding

    /*
     * ViewModel property used for data access.
     */
    protected abstract val viewModel: ViewModel

    /*
     * The `activity` property cast to MainActivity so that further
     * casts are not needed and the functions of MainActivity can
     * be safely accessed. It is assumed that this application
     * will be designed around a single activity, so that the
     * value of `activity` should always successfully cast to
     * MainActivity; also possible is that this Fragment is always
     * associated to a MainActivity.
     */
    protected val mainActivity: MainActivity = activity as MainActivity

    /*
     * Used for finding out how many elements horizontally fit on
     * the screen.
     */
    protected val spanCount: Int get() = mainActivity.getScreenWidth() / 300

    /*
     * A non-null context is grabbed in onAttach, which means
     * that the context is unavailable if the fragment has not
     * been initialised.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        _context = context
    }

    /**
     * Returns a non-null context if the Fragment is initialised.
     *
     * @return  Context
     * @throws  IllegalStateException if the Fragment is not ready
     *          (not yet initialised or already destroyed)
     */
    @Throws(IllegalStateException::class)
    override fun getContext(): Context {
        if (!viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException(
                "Context unavailable when ${javaClass.simpleName} is uninitialised."
            )
        }
        return _context
    }
}