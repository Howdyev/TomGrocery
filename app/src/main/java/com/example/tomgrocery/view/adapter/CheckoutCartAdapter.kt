package com.example.tomgrocery.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tomgrocery.R
import com.example.tomgrocery.constants.Constants
import com.example.tomgrocery.databinding.RowCartBinding
import com.example.tomgrocery.model.local.entity.Cart
import com.squareup.picasso.Picasso

class CheckoutCartAdapter (val context: Context, private val itemList: MutableList<Cart>): RecyclerView.Adapter<CheckoutCartAdapter.CheckoutCartViewHolder>() {
    private lateinit var binding: RowCartBinding

    inner class CheckoutCartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(cart: Cart) {
            binding.apply {
                productTitle.text = cart.productName
                productAttribute.text = cart.description
                productCurrency.text = "$"
                productPrice.text = "%.2f".format(cart.price)
                val url = Constants.BASE_IMAGE_URL + cart.productImg
                try {
                    Picasso
                        .get()
                        .load(url)
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                        .into(binding.productImage)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
                subTotal.text = cart.totalPrice.toString()
                quantity.text = cart.quantity.toString()
                binding.quantityPlus.visibility = View.GONE
                binding.quantityMinus.visibility = View.GONE
                binding.cartDelete.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutCartViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        binding = RowCartBinding.inflate(layoutInflater, parent, false)
        return CheckoutCartViewHolder(binding.root)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: CheckoutCartViewHolder, position: Int) {
        holder.apply {
            val cart = itemList[position]
            bind(cart)
        }
    }
}