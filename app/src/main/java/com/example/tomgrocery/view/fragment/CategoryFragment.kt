package com.example.tomgrocery.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomgrocery.viewmodel.DashboardViewModel
import com.example.tomgrocery.databinding.FragmentCategoryBinding
import com.example.tomgrocery.model.remote.ApiClient
import com.example.tomgrocery.model.remote.ApiService
import com.example.tomgrocery.model.repository.LocalRepository
import com.example.tomgrocery.model.repository.RemoteRepository
import com.example.tomgrocery.model.repository.Repository
import com.example.tomgrocery.util.localstorage.LocalStorage
import com.example.tomgrocery.view.activity.DashboardActivity
import com.example.tomgrocery.view.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private lateinit var localStorage: LocalStorage
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var containerActivity: DashboardActivity
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        containerActivity = requireActivity() as DashboardActivity
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
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