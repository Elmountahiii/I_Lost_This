package com.redgunner.ilostthis.view.fragment.byCategory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.adapter.IFoundThisAdapter
import com.redgunner.ilostthis.state.PostClick
import com.redgunner.ilostthis.view.fragment.foundAndLost.CategoryFragmentDirections
import com.redgunner.ilostthis.view.fragment.foundAndLost.MainFragmentDirections
import com.redgunner.ilostthis.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_by_category_found.*
import kotlinx.android.synthetic.main.fragment_by_location_lost.*
import kotlinx.android.synthetic.main.fragment_i_found_this.*

class ByCategoryFoundFragment : Fragment(R.layout.fragment_by_category_found) {
    private val viewModel: MainViewModel by activityViewModels()

    private val categoryItems = listOf("Phones", "Clothes", "Accessories", "Money")

    private val iFoundThisAdapter= IFoundThisAdapter{ postClick ->

        when(postClick){
            is PostClick.ItemFound ->{
                findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToDetailsFragment(postClick.postId,postClick.isLost))
            }
            else -> {

            }
        }


    }


    override fun onStart() {
        super.onStart()

        setUpRecyclerView()
        setUpCategoriesTabLayout()

        viewModel.getFoundItem()

    }

    override fun onResume() {
        super.onResume()
        viewModel.foundItemsListByCategory.observe(viewLifecycleOwner,{foundItems ->
            Log.d("BigKoma","redy to view the size is ${foundItems.size} ")

            for (item in foundItems){
                Log.d("BigKoma","${item.title}")

            }

            iFoundThisAdapter.submitList(foundItems)

        })

        foundCategoryFragmentTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                Log.d("BigKoma","tab selcted is ${tab!!.text.toString()}")

                viewModel.getFoundItemsByCategory(tab!!.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun setUpRecyclerView() {
        byCategoryIFoundList.apply {
            this.adapter=iFoundThisAdapter
        }
    }

    private fun setUpCategoriesTabLayout() {
        for(category in categoryItems){
            val tab = foundCategoryFragmentTabLayout.newTab()
            tab.text = category
            foundCategoryFragmentTabLayout.addTab(tab)
        }
    }
}