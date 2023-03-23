package com.example.tomgrocery.view.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tomgrocery.databinding.OrderdetailsDialogBinding
import com.example.tomgrocery.databinding.RowOrderBinding
import com.example.tomgrocery.databinding.SuccessDialogBinding
import com.example.tomgrocery.model.remote.dto.MyOrdersData

class OrderAdapter (
    private val context: Context,
    private val orderList: List<MyOrdersData>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private lateinit var binding: RowOrderBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        binding = RowOrderBinding.inflate(layoutInflater, parent, false)
        return OrderViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.apply {
            val order = orderList[position]
            bind(order)
            itemView.setOnClickListener {
                openOrderItemModal(order)
            }
        }
    }

    private fun openOrderItemModal(order: MyOrdersData) {
        val layoutInflater = LayoutInflater.from(context)
        val dialogOrderDetail: OrderdetailsDialogBinding = OrderdetailsDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(context).apply {
            setView(dialogOrderDetail.root)
            setCancelable(false)
        }
        val dialog = builder.create()
        dialog.window?.setGravity(Gravity.CENTER)
        dialogOrderDetail.apply {
            dialogButtonOK.setOnClickListener {
                dialog.dismiss()
            }
            orderList.layoutManager = LinearLayoutManager(context)
            orderList.adapter = ProductInOrderAdapter(context, order.products)
        }
        dialog.show()
    }

    override fun getItemCount() = orderList.size

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(order: MyOrdersData) {
            binding.orderId.text = "#" + order._id
            val year = order.date.substring(0,4)
            val month = order.date.substring(5,7)
            val day = order.date.substring(8,10)
            binding.date.text = "$month/$day/$year"
            binding.totalAmount.text = "Total: $" + order.orderSummary.totalAmount.toString()
            binding.status.text = "Pending"
        }
    }
}