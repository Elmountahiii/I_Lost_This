package com.redgunner.ilostthis.view.fragment.foundAndLost


import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.redgunner.ilostthis.R
import com.redgunner.ilostthis.adapter.MainStateAdapter
import com.redgunner.ilostthis.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModels()


    override fun onStart() {
        super.onStart()
        setUpTapLayout()

    }

    private fun setUpTapLayout() {
        mainFragmentViewPager.adapter=MainStateAdapter(this)
        TabLayoutMediator(mainFragmentTabLayout, mainFragmentViewPager) { tab, position ->

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


    override fun onResume() {
        super.onResume()
        drawerNavigationView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.CategorySearch -> {

                    findNavController().navigate(R.id.action_mainFragment_to_categoryFragment)
                    true
                }
                R.id.itemSearch -> {
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.LocationSearch -> {

                    findNavController().navigate(R.id.action_mainFragment_to_locationFragment)

                    true
                }

                else -> false
            }
        }
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }


        floating_action_button.setOnClickListener {


            findNavController().navigate(R.id.action_mainFragment_to_declareItemFragment)

        }
    }


}