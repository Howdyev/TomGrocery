package com.example.tomgrocery.view.adapter.fragmentadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.tomgrocery.view.fragment.subcategories.BarbequeFragment
import com.example.tomgrocery.view.fragment.subcategories.CombosFragment
import com.example.tomgrocery.view.fragment.subcategories.FriedFragment

class SubCatChickenFragmentAdapter(fm: FragmentManager, private val subCount:Int) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return subCount
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0-> FriedFragment()
            1 -> BarbequeFragment()
            else -> CombosFragment()
        }
    }
}