package com.redgunner.ilostthis.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.redgunner.ilostthis.view.fragment.bylocation.ByLocationFoundFragment
import com.redgunner.ilostthis.view.fragment.bylocation.ByLocationLostFragment
import com.redgunner.ilostthis.view.fragment.foundAndLost.IFoundThisFragment
import com.redgunner.ilostthis.view.fragment.foundAndLost.ILostThisFragment
import com.redgunner.ilostthis.view.fragment.foundAndLost.LocationFragment

class LocationStateAdapter(fragmentActivity: LocationFragment) :
        FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){

            0-> ByLocationFoundFragment()
            else -> ByLocationLostFragment()
        }

    }
}