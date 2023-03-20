package com.example.tomgrocery.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tomgrocery.R
import com.example.tomgrocery.constants.Constants
import com.example.tomgrocery.databinding.RowCartBinding
import com.example.tomgrocery.databinding.RowProductBinding
import com.example.tomgrocery.model.local.entity.Cart
import com.example.tomgrocery.model.remote.dto.Product
import com.example.tomgrocery.util.MyToast
import com.example.tomgrocery.view.activity.CartActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class CartAdapter(val context: Context, private val itemList: MutableList<Cart>): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private lateinit var binding: RowCartBinding
    private val cartManager: CartActivity = context as CartActivity

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
                        .error(R.drawable.no_image)
                        .into(binding.productImage, object: Callback {
                            override fun onSuccess() {
                                binding.progressbar.visibility = View.GONE
                            }

                            override fun onError(e: Exception?) {
                                binding.progressbar.visibility = View.GONE
                            }
                        })
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
                subTotal.text = cart.totalPrice.toString()
                quantity.text = cart.quantity.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        binding = RowCartBinding.inflate(layoutInflater, parent, false)
        return CartViewHolder(binding.root)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.apply {
            val cart = itemList[position]
            bind(cart)
        }
        buttonEvents(itemList[position])
        binding.cartDelete.setOnClickListener {
            cartManager.deleteCart(itemList[position])
            itemList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemList.size)
            cartManager.updateTotalPrice()
        }
    }
    private fun buttonEvents(cart: Cart) {
        binding.apply {
            quantityPlus.setOnClickListener {
                var temp = quantity.text.toString().toInt()
                temp++
                quantity.setText(temp.toString())
                cartManager.updateCartItem(temp, cart.productId)?.let {
                    subTotal.text = "%.2f".format(it.totalPrice)
                    cartManager.updateTotalPrice()
                }
            }
            quantityMinus.setOnClickListener {
                var temp = quantity.text.toString().toInt()
                temp--
                if(temp < 1) {
                    temp = 1
                }
                quantity.setText(temp.toString())
                cartManager.updateCartItem(temp, cart.productId)?.let {
                    subTotal.text = "%.2f".format(it.totalPrice)
                    cartManager.updateTotalPrice()
                }
            }
        }
    }
}