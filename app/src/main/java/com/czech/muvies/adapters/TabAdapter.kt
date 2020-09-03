package com.czech.muvies.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(fragment: Fragment, var fragments: List<Fragment>): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}