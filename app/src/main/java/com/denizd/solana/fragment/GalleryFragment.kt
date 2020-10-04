package com.denizd.solana.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.denizd.solana.R
import com.denizd.solana.adapter.GalleryAdapter
import com.denizd.solana.databinding.FragmentGalleryBinding
import com.denizd.solana.util.viewBinding
import com.denizd.solana.viewmodel.GalleryViewModel

/*
 * Fragment used for displaying available images to the user.
 * This is the app's main interface and shown to the user
 * on app start.
 */
class GalleryFragment : BaseFragment(R.layout.fragment_gallery) {

    override val binding: FragmentGalleryBinding by viewBinding(FragmentGalleryBinding::bind)
    override val viewModel: ViewModel by viewModels<GalleryViewModel>()

    private val galleryAdapter: GalleryAdapter = GalleryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(context, spanCount)
        }
    }
}