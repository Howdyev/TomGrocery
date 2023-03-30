package com.example.tomgrocery.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tomgrocery.viewmodel.DashboardViewModel
import com.example.tomgrocery.databinding.FragmentProductsBinding
import com.example.tomgrocery.view.adapter.fragmentadapter.SubCatBeefFragmentAdapter
import com.example.tomgrocery.view.adapter.fragmentadapter.SubCatChickenFragmentAdapter
import com.example.tomgrocery.view.adapter.fragmentadapter.SubCatFruitsFragmentAdapter
import com.example.tomgrocery.view.adapter.fragmentadapter.SubCatVegFragmentAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        viewModel.getSubCategory()

    }

    private fun setupObserver() {
        viewModel.subCategories.observe(viewLifecycleOwner) {
            when (DashboardViewModel.staticCategoryId) {
                1 -> {
                    val fm = SubCatChickenFragmentAdapter(childFragmentManager, it.size)
                    childFragmentManager.beginTransaction()
                    binding.viewPager.adapter = fm
                    binding.tabLayout.setupWithViewPager(binding.viewPager)
                    for (i in it.indices) {
                        binding.tabLayout.getTabAt(i)?.text = it[i].subName
                    }
                }
                2 -> {
                    val fm = SubCatVegFragmentAdapter(childFragmentManager, it.size)
                    childFragmentManager.beginTransaction()
                    binding.viewPager.adapter = fm
                    binding.tabLayout.setupWithViewPager(binding.viewPager)
                    for (i in it.indices) {
                        binding.tabLayout.getTabAt(i)?.text = it[i].subName
                    }
                }
                3 -> {
                    val fm = SubCatFruitsFragmentAdapter(childFragmentManager, it.size)
                    childFragmentManager.beginTransaction()
                    binding.viewPager.adapter = fm
                    binding.tabLayout.setupWithViewPager(binding.viewPager)
                    for (i in it.indices) {
                        binding.tabLayout.getTabAt(i)?.text = it[i].subName
                    }
                }
                4 -> {
                    val fm = SubCatBeefFragmentAdapter(childFragmentManager, it.size)
                    childFragmentManager.beginTransaction()
                    binding.viewPager.adapter = fm
                    binding.tabLayout.setupWithViewPager(binding.viewPager)
                    for (i in it.indices) {
                        binding.tabLayout.getTabAt(i)?.text = it[i].subName
                    }
                }
            }
        }
        viewModel.isProcessing.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.root.visibility = View.VISIBLE
            } else {
                binding.progressBar.root.visibility = View.GONE
            }
        }
    }
}