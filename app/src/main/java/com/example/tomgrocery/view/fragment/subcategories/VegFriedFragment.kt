package com.example.tomgrocery.view.fragment.subcategories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomgrocery.databinding.ProductListBinding
import com.example.tomgrocery.viewmodel.DashboardViewModel
import com.example.tomgrocery.model.remote.dto.Product
import com.example.tomgrocery.view.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VegFriedFragment : Fragment() {
    private lateinit var binding: ProductListBinding
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = ProductListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProductsBySubId(4)
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
}