package com.redgunner.ilostthis.view.fragment.bylocation

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.adapter.IFoundThisAdapter
import com.redgunner.ilostthis.state.PostClick
import com.redgunner.ilostthis.utils.Location
import com.redgunner.ilostthis.view.fragment.foundAndLost.CategoryFragmentDirections
import com.redgunner.ilostthis.view.fragment.foundAndLost.LocationFragmentDirections
import com.redgunner.ilostthis.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_by_category_found.*
import kotlinx.android.synthetic.main.fragment_by_location_found.*
import kotlinx.android.synthetic.main.fragment_by_location_lost.*


class ByLocationFoundFragment : Fragment(R.layout.fragment_by_location_found) {
    private val viewModel: MainViewModel by activityViewModels()



    private val iFoundThisAdapter= IFoundThisAdapter{ postClick ->

        when(postClick){
            is PostClick.ItemFound ->{
                findNavController().navigate(LocationFragmentDirections.actionLocationFragmentToDetailsFragment(postClick.postId,postClick.isLost))
            }
            else -> {

            }
        }


    }


    override fun onStart() {
        super.onStart()

        setUpRecyclerView()

    }

    override fun onResume() {
        super.onResume()
        viewModel.foundItemsListByLocation.observe(viewLifecycleOwner,{foundItems ->
            iFoundThisAdapter.submitList(foundItems)

        })

        viewModel.locationList.observe(viewLifecycleOwner,{list ->

            setUpCategoriesTabLayout(list)

        })



        foundLocationFragmentTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {


                viewModel.getFoundItemsByLocation(tab!!.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }

    private fun setUpRecyclerView() {
        byLocationIFoundList.apply {
            this.adapter=iFoundThisAdapter
        }
    }


    private fun setUpCategoriesTabLayout(list :List<Location>) {

        foundLocationFragmentTabLayout.removeAllTabs()

        for (location in list){
            val tab = foundLocationFragmentTabLayout.newTab()
            tab.text = location.location
            foundLocationFragmentTabLayout.addTab(tab)
        }

    }


}