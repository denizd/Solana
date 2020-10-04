package com.denizd.solana.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowInsets
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.denizd.solana.R
import com.denizd.solana.databinding.ActivityMainBinding
import com.denizd.solana.fragment.GalleryFragment
import com.denizd.solana.util.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        if (supportFragmentManager.fragments.size == 0) {
            supportFragmentManager.beginTransaction().apply {
                listOf(GalleryFragment()).forEach { fragment ->
                    add(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                    if (fragment.javaClass.simpleName == GalleryFragment::class.java.simpleName) {
                        show(fragment)
                    } else {
                        hide(fragment)
                    }
                }
            }.commit()
        }
    }

    private fun showFragment(name: String) {
        supportFragmentManager.beginTransaction().apply {
            hide(supportFragmentManager.fragments.getCurrentFragment())
            show(fragmentOrThrow(name))
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }.commit()
    }

    private fun fragmentOrThrow(name: String): Fragment =
        supportFragmentManager.findFragmentByTag(name)
            ?: throw IllegalStateException("Fragment $name is not initialized")

    private fun List<Fragment>.getCurrentFragment(): Fragment {
        forEach { fragment -> if (fragment.isVisible) return fragment }
        throw IllegalStateException("No fragment currently visible")
    }

    fun getScreenWidth(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            DisplayMetrics().also { displayMetrics ->
                windowManager.defaultDisplay.getMetrics(displayMetrics)
            }.widthPixels
        }
    }
}