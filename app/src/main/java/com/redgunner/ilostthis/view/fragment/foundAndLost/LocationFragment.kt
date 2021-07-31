package com.redgunner.ilostthis.view.fragment.foundAndLost

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.adapter.CategoryStateAdapter
import com.redgunner.ilostthis.adapter.LocationStateAdapter
import com.redgunner.ilostthis.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_category.categoryFragmentTabLayout
import kotlinx.android.synthetic.main.fragment_location.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.topAppBar


class LocationFragment : Fragment(R.layout.fragment_location) {

    private val viewModel: MainViewModel by activityViewModels()


    override fun onStart() {
        super.onStart()
        setUpTapLayout()
        viewModel.getLocations()


    }

    override fun onResume() {
        super.onResume()
        topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()        }

    }

    private fun setUpTapLayout() {
        locationFragmentViewPager.adapter= LocationStateAdapter(this)
        TabLayoutMediator(locationFragmentTabLayout, locationFragmentViewPager) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = "Found Items"
                }
                1 -> {
                    tab.text = "Lost items"
                }
            }

        }.attach()
    }
}