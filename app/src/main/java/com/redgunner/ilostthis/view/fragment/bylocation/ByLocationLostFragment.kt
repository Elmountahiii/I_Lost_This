package com.redgunner.ilostthis.view.fragment.bylocation

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
import com.redgunner.ilostthis.view.fragment.foundAndLost.MainFragmentDirections
import com.redgunner.ilostthis.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_by_category_found.*
import kotlinx.android.synthetic.main.fragment_by_category_lost.*
import kotlinx.android.synthetic.main.fragment_by_location_found.*
import kotlinx.android.synthetic.main.fragment_by_location_lost.*
import kotlinx.android.synthetic.main.fragment_i_lost_this.*
import java.util.concurrent.atomic.AtomicBoolean


class ByLocationLostFragment : Fragment(R.layout.fragment_by_location_lost) {

    private val viewModel: MainViewModel by activityViewModels()



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


    }

    override fun onResume() {
        super.onResume()
        viewModel.lostItemsListByLocation.observe(viewLifecycleOwner, { lostItems ->


            iLostThisAdapter.submitList(lostItems)

        })


        lostLocationFragmentTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {


                viewModel.getLostItemsByLocation(tab!!.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })




    }

    private fun setUpRecyclerView() {
        byLocationILostList.apply {
            this.adapter = iLostThisAdapter
            this.layoutManager = LinearLayoutManager(
                    this@ByLocationLostFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false
            )
            this.hasFixedSize()
        }
    }

    private fun setUpCategoriesTabLayout() {

        for (location in viewModel.locationList.value!!){
            val tab = lostLocationFragmentTabLayout.newTab()
            tab.text = location.location
            lostLocationFragmentTabLayout.addTab(tab)

        }



    }





}