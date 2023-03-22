package com.example.tomgrocery.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.tomgrocery.R
import com.example.tomgrocery.databinding.FragmentMyOrderBinding
import com.example.tomgrocery.model.remote.dto.MyOrdersData
import com.example.tomgrocery.util.MyToast
import com.example.tomgrocery.util.localstorage.LocalStorage
import com.example.tomgrocery.view.adapter.OrderAdapter
import com.example.tomgrocery.viewmodel.AuthViewModel
import com.example.tomgrocery.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrderFragment : Fragment() {

    private lateinit var binding: FragmentMyOrderBinding
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var localStorage: LocalStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyOrderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        localStorage = LocalStorage(requireContext().applicationContext)
        setupObserver()
        viewModel.myOrders(localStorage.getUserId())
    }

    private fun setupObserver() {
        viewModel.isProcessing.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.root.visibility = View.VISIBLE
            } else {
                binding.progressBar.root.visibility = View.GONE
            }
        }
        viewModel.myOrdersResponse.observe(viewLifecycleOwner) {
            MyToast.showToast(requireContext(),"My order success!")
            setupOrderRecycleView(it.data)
        }
    }

    private fun setupOrderRecycleView(orders: List<MyOrdersData>) {
        if(orders.isEmpty()) {
            binding.noOrderLl.visibility = View.VISIBLE
        } else {
            binding.noOrderLl.visibility = View.GONE
        }
        binding.orderRv.adapter = OrderAdapter(requireContext(), orders)
        binding.orderRv.layoutManager = LinearLayoutManager(requireContext())
        binding.orderRv.itemAnimator = DefaultItemAnimator()
    }
}