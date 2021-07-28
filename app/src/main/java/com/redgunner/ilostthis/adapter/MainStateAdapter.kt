package com.redgunner.ilostthis.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.redgunner.ilostthis.view.fragment.foundAndLost.IFoundThisFragment
import com.redgunner.ilostthis.view.fragment.foundAndLost.ILostThisFragment
import com.redgunner.ilostthis.view.fragment.foundAndLost.MainFragment

class MainStateAdapter(fragmentActivity: MainFragment) :
        FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){

            0-> IFoundThisFragment()
            else -> ILostThisFragment()
        }

    }
}