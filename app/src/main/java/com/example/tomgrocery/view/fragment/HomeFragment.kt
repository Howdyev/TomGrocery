package com.example.tomgrocery.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomgrocery.viewmodel.DashboardViewModel
import com.example.tomgrocery.databinding.FragmentHomeBinding
import com.example.tomgrocery.view.activity.DashboardActivity
import com.example.tomgrocery.view.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var containerActivity: DashboardActivity
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        containerActivity = requireActivity() as DashboardActivity
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        viewModel.getCategories()
    }

    private fun setupObserver() {
        viewModel.categoryList.observe(viewLifecycleOwner) {
            binding.categoryRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.categoryRv.adapter = CategoryAdapter(requireContext(), it)
        }
        viewModel.isProcessing.observe(viewLifecycleOwner) {
            if(it) {
                binding.progressBar.root.visibility = View.VISIBLE
            } else {
                binding.progressBar.root.visibility = View.GONE
            }
        }
    }
}