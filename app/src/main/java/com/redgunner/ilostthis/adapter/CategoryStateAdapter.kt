package com.redgunner.ilostthis.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.redgunner.ilostthis.view.fragment.byCategory.ByCategoryFoundFragment
import com.redgunner.ilostthis.view.fragment.byCategory.ByCategoryLostFragment
import com.redgunner.ilostthis.view.fragment.foundAndLost.CategoryFragment
import com.redgunner.ilostthis.view.fragment.foundAndLost.IFoundThisFragment
import com.redgunner.ilostthis.view.fragment.foundAndLost.ILostThisFragment

class CategoryStateAdapter (fragmentActivity: CategoryFragment) :
        FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){

            0-> ByCategoryFoundFragment()
            else -> ByCategoryLostFragment()
        }

    }
}