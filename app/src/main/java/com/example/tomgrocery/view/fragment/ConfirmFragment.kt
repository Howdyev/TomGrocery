package com.example.tomgrocery.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomgrocery.R
import com.example.tomgrocery.databinding.FragmentConfirmBinding
import com.example.tomgrocery.databinding.SuccessDialogBinding
import com.example.tomgrocery.model.remote.dto.*
import com.example.tomgrocery.util.MyToast
import com.example.tomgrocery.util.localstorage.LocalStorage
import com.example.tomgrocery.view.activity.CheckoutActivity
import com.example.tomgrocery.view.activity.DashboardActivity
import com.example.tomgrocery.view.adapter.CheckoutCartAdapter
import com.example.tomgrocery.viewmodel.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ConfirmFragment : Fragment() {

    private lateinit var binding: FragmentConfirmBinding
    private val viewModel: CheckoutViewModel by viewModels()
    private lateinit var localStorage: LocalStorage
    private lateinit var cartManager: CheckoutActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfirmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        localStorage = LocalStorage(requireContext().applicationContext)
        cartManager = requireActivity() as CheckoutActivity
        activity?.title = "Confirm"
        initViews()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.isProcessing.observe(viewLifecycleOwner) {
            if(it) {
                binding.progressBar.root.visibility = View.VISIBLE
            } else {
                binding.progressBar.root.visibility = View.GONE
            }
        }
        viewModel.placeOrderResult.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                MyToast.showToast(requireContext(), it)
            }
        }
        viewModel.placeOrderResponse.observe(viewLifecycleOwner) {
            showSuccessDialog()
        }
    }

    private fun showSuccessDialog() {
        val dialogLoginBinding: SuccessDialogBinding = SuccessDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext()).apply {
            setView(dialogLoginBinding.root)
            setCancelable(false)
        }
        val dialog = builder.create()
        dialog.window?.setGravity(Gravity.CENTER)
        dialogLoginBinding.placeOrderSuccess.setOnClickListener {
            openDashboard()
        }
        dialog.show()
    }

    private fun setupCartRecyclerview() {
        binding.cartRv.setHasFixedSize(true)
        binding.cartRv.layoutManager = LinearLayoutManager(context)
        binding.cartRv.adapter = CheckoutCartAdapter(requireContext(), cartManager.getAllCartItems())
    }

    private fun initViews() {
        val _total = cartManager.getTotalPrice()
        val _shipping = 0.0
        val _totalAmount = _total + _shipping
        binding.total.text = "%.2f".format(_total)
        binding.shippingAmount.text = "%.2f".format(_shipping)
        binding.totalAmount.text = "\$%.2f".format(_totalAmount)
        binding.back.setOnClickListener {
            openDashboard()
        }
        setupCartRecyclerview()
        binding.placeOrder.setOnClickListener {
            placeOrder()
        }
    }

    private fun placeOrder() {
        val addressList = cartManager.getAllAddressItems()
        if(addressList.isEmpty()) {
            MyToast.showToast(requireContext(), "No address!")
            return
        }
        val savedAddress = addressList.last()

        val shippingAddress = ShippingAddress(
            city = savedAddress.city,
            houseNo = savedAddress.houseNo,
            pincode = savedAddress.pincode,
            streetName = savedAddress.streetName,
            type = savedAddress.type
        )
        val orderSummary = OrderSummaryToOrder(10,10,10, cartManager.getTotalPrice())
        val userToOrder = UserToOrder(
            email = localStorage.getUserEmail(),
            orderStatus = "completed"
        )
        val products = mutableListOf<ProductToOrder>()
        val cartItems = cartManager.getAllCartItems()
        cartItems.forEach {
            val product = ProductToOrder(
                price = it.price,
                quantity = it.quantity,
                productName = it.productName
            )
            products.add(product)
        }
        val placeOrderRequestData = PlaceOrderRequestData (
            userId = localStorage.getUserId(),
            orderSummary = orderSummary,
            products = products,
            shippingAddress = shippingAddress,
            user = userToOrder
        )
        viewModel.placeOrder(placeOrderRequestData)
    }

    private fun openDashboard() {
        startActivity(Intent(context, DashboardActivity::class.java))
        activity?.finish()
    }
}