package com.example.tomgrocery.view.fragment.subcategories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

class BeefKabobFragment: Fragment(){
    private lateinit var binding: ProductListBinding
    private lateinit var viewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        viewModel.getProductsBySubId(9)
        setResult()
    }

    private fun setResult() {
        viewModel.isProcessing.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.root.visibility = View.VISIBLE
            } else {
                binding.progressBar.root.visibility = View.GONE
            }
        }

        viewModel.products.observe(viewLifecycleOwner) {
            val list: MutableList<Product> = mutableListOf()
            for (i in it.indices) {
                list.add(it[i])
            }
            val adapter = ProductAdapter(requireActivity(),list)
            binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
            binding.rvItems.adapter = adapter
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