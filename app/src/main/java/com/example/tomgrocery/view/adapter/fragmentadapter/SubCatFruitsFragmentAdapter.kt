package com.example.tomgrocery.view.adapter.fragmentadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.tomgrocery.view.fragment.subcategories.FruitsFragment
import com.example.tomgrocery.view.fragment.subcategories.VegetablesFragment

class SubCatFruitsFragmentAdapter(fm: FragmentManager, private val subCount:Int) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return subCount
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0-> FruitsFragment()
            1 -> VegetablesFragment()
            else -> VegetablesFragment()
        }
    }
}