package com.example.tomgrocery.view.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.tomgrocery.databinding.OrderdetailsDialogBinding
import com.example.tomgrocery.databinding.OrderdetailsItemBinding
import com.example.tomgrocery.databinding.RowOrderBinding
import com.example.tomgrocery.model.remote.dto.MyOrdersData
import com.example.tomgrocery.model.remote.dto.Product

class ProductInOrderAdapter (
    private val context: Context,
    private val productsList: List<Product>) :
    RecyclerView.Adapter<ProductInOrderAdapter.ProductInOrderViewHolder>() {

    private lateinit var binding: OrderdetailsItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductInOrderViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        binding = OrderdetailsItemBinding.inflate(layoutInflater, parent, false)
        return ProductInOrderViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ProductInOrderViewHolder, position: Int) {
        holder.apply {
            val product = productsList[position]
            bind(product)
        }
    }

    override fun getItemCount() = productsList.size

    inner class ProductInOrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(product: Product) {
            binding.orderId.text = "#" + product._id
            binding.orderQuantity.text = product.quantity.toString()
            binding.orderTitle.text = product.productName
            binding.orderUnitprice.text = "\$ %.2f".format(product.price)
            binding.totalPrice.text = "\$ %.2f".format(product.price * product.quantity)
        }
    }
}