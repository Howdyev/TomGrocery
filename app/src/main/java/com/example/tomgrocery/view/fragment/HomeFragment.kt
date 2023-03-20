package com.example.tomgrocery.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomgrocery.viewmodel.DashboardFactory
import com.example.tomgrocery.viewmodel.DashboardViewModel
import com.example.tomgrocery.databinding.FragmentHomeBinding
import com.example.tomgrocery.model.remote.ApiClient
import com.example.tomgrocery.model.remote.ApiService
import com.example.tomgrocery.model.repository.LocalRepository
import com.example.tomgrocery.model.repository.RemoteRepository
import com.example.tomgrocery.model.repository.Repository
import com.example.tomgrocery.view.activity.DashboardActivity
import com.example.tomgrocery.view.adapter.CategoryAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var containerActivity: DashboardActivity
    private lateinit var viewModel: DashboardViewModel

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
        setupViewModel()
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

    private fun setupViewModel() {
        val localRepository = LocalRepository()
        val remoteRepository = RemoteRepository(ApiClient.retrofit.create(ApiService::class.java))
        val repository = Repository(localRepository, remoteRepository)
        val factory = DashboardFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory)[DashboardViewModel::class.java]
    }
}