package com.example.tomgrocery.view.adapter.fragmentadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.tomgrocery.view.fragment.subcategories.VegCombos
import com.example.tomgrocery.view.fragment.subcategories.VegFriedFragment

class SubCatVegFragmentAdapter(fm: FragmentManager, private val subCount:Int) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return subCount
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0-> VegFriedFragment()
            1 -> VegCombos()
            else -> VegCombos()
        }
    }
}