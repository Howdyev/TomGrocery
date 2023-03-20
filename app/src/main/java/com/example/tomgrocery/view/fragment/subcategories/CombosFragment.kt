package com.example.tomgrocery.view.fragment.subcategories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomgrocery.databinding.ProductListBinding
import com.example.tomgrocery.viewmodel.DashboardFactory
import com.example.tomgrocery.viewmodel.DashboardViewModel
import com.example.tomgrocery.model.remote.dto.Product
import com.example.tomgrocery.view.adapter.ProductAdapter
import com.example.tomgrocery.model.remote.ApiClient
import com.example.tomgrocery.model.remote.ApiService
import com.example.tomgrocery.model.repository.LocalRepository
import com.example.tomgrocery.model.repository.RemoteRepository
import com.example.tomgrocery.model.repository.Repository

class CombosFragment : Fragment(){
    private lateinit var binding: ProductListBinding
    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = ProductListBinding.inflate(layoutInflater)
        setupViewModel()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProductsBySubId(3)
        setResult()
    }

    private fun setResult() {
        viewModel.products.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val adapter = ProductAdapter(requireContext(),it)
                binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
                binding.rvItems.adapter = adapter
            } else {
                binding.rvItems.visibility = View.GONE
                binding.txtNoItems.visibility = View.VISIBLE
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