package com.redgunner.ilostthis.view.fragment.byCategory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.adapter.ILostThisAdapter
import com.redgunner.ilostthis.state.PostClick
import com.redgunner.ilostthis.view.fragment.foundAndLost.CategoryFragmentDirections
import com.redgunner.ilostthis.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_by_category_lost.*
import kotlinx.android.synthetic.main.fragment_by_location_lost.*
import java.util.concurrent.atomic.AtomicBoolean


class ByCategoryLostFragment : Fragment(R.layout.fragment_by_category_lost) {

    private val viewModel: MainViewModel by activityViewModels()

    private val categoryItems = listOf("Phones", "Clothes", "Accessories", "Money","Other")


    private val iLostThisAdapter = ILostThisAdapter { postClick ->
        when (postClick) {
            is PostClick.ItemLost -> {
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


        viewModel.getLostItems()



    }


    override fun onResume() {
        super.onResume()
        viewModel.lostItemsListByCategory.observe(viewLifecycleOwner, { lostItems ->


            iLostThisAdapter.submitList(lostItems)

        })

        lostcategoryFragmentTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {




            override fun onTabSelected(tab: TabLayout.Tab?) {

                Log.d("youusssh","onTabSelected : ${tab!!.text.toString()}")


                viewModel.getLostItemsByCategory(tab!!.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("youusssh","onTabUnselected : ${tab!!.text.toString()}")

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

                Log.d("youusssh","onTabReselected : ${tab!!.text.toString()}")

            }

        })

    }

    private fun setUpRecyclerView() {
        byCategoryILostList.apply {
            this.adapter = iLostThisAdapter
            this.layoutManager = LinearLayoutManager(
                    this@ByCategoryLostFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false
            )
            this.hasFixedSize()
        }
    }



    private fun setUpCategoriesTabLayout() {
        for(category in categoryItems){
            val tab = lostcategoryFragmentTabLayout.newTab()
            tab.text = category
            lostcategoryFragmentTabLayout.addTab(tab)

        }
    }


}