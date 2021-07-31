package com.redgunner.ilostthis.view.fragment.foundAndLost

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.adapter.CategoryStateAdapter
import com.redgunner.ilostthis.adapter.MainStateAdapter
import com.redgunner.ilostthis.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.topAppBar

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private val viewModel: MainViewModel by activityViewModels()





    override fun onStart() {
        super.onStart()
        setUpTapLayout()

    }



    override fun onResume() {
        super.onResume()
        topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }


    private fun setUpTapLayout() {
        categoryFragmentViewPager.adapter= CategoryStateAdapter(this)
        TabLayoutMediator(categoryFragmentTabLayout, categoryFragmentViewPager) { tab, position ->

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