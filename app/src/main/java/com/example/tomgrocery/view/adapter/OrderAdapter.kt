package com.example.tomgrocery.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tomgrocery.databinding.RowOrderBinding
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
        }
    }

    override fun getItemCount() = orderList.size

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(order: MyOrdersData) {
//            try {
//                Picasso
//                    .get()
//                    .load(Constants.BASE_IMAGE_URL + category.catImage)
//                    .placeholder(R.drawable.no_image)
//                    .error(R.drawable.no_image)
//                    .into(binding.categoryImage)
//                binding.progressbar.visibility = View.GONE
//
//            } catch (e: java.lang.Exception) {
//                e.printStackTrace()
//                binding.progressbar.visibility = View.GONE
//            }
//            binding.categoryTitle.text = category.catName
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