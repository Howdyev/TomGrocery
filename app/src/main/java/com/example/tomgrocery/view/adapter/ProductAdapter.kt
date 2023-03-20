package com.example.tomgrocery.view.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tomgrocery.constants.Constants
import com.example.tomgrocery.databinding.RowProductBinding
import com.example.tomgrocery.model.local.AppDatabase
import com.example.tomgrocery.model.local.dao.CartDao
import com.example.tomgrocery.model.local.entity.Cart
import com.example.tomgrocery.model.remote.dto.Product
import com.example.tomgrocery.util.MyToast
import com.example.tomgrocery.view.activity.DashboardActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class ProductAdapter (val context: Context, private val itemList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private lateinit var binding: RowProductBinding
    private val cartManager: DashboardActivity = context as DashboardActivity

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            binding.apply {
                val rnd = (0..3).random()
                val specialOffers = listOf("", "Special Offer", "Best Price", "Top Rated")
                productDiscount.text = specialOffers[rnd]
                if(rnd == 0) {
                    productDiscount.visibility = View.GONE
                }
                productTitle.text = product.productName
                productAttribute.text = product.description
                productCurrency.text = "$"
                productPrice.text = "%.2f".format(product.price)
                val url = Constants.BASE_IMAGE_URL + product.image
                try {
                    Picasso.get().load(url).into(binding.productImage)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
                val cartItem = cartManager.getCartItem(product._id)
                cartItem?.let {
                    addToCart.visibility = View.GONE
                    subTotal.visibility = View.VISIBLE
                    subTotal.text = "$ " + cartItem.totalPrice.toString()
                    quantity.text = cartItem.quantity.toString()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        binding = RowProductBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.apply {
            val product = itemList[position]
            bind(product)
        }
        buttonEvents(itemList[position])
    }

    private fun buttonEvents(product: Product) {
        binding.apply {
            quantityPlus.setOnClickListener {
                var temp = quantity.text.toString().toInt()
                temp++
                quantity.setText(temp.toString())
                cartManager.updateCartItem(temp, product._id)?.let {
                    subTotal.text = "$ " + it.totalPrice
                }
            }
            quantityMinus.setOnClickListener {
                var temp = quantity.text.toString().toInt()
                temp--
                if(temp < 1) {
                    temp = 1
                }
                quantity.setText(temp.toString())
                cartManager.updateCartItem(temp, product._id)?.let {
                    subTotal.text = "$ " + it.totalPrice
                }
            }
            addToCart.setOnClickListener {
                val temp = quantity.text.toString().toInt()
                val cartItem = Cart(
                    index = 0,
                    productName = product.productName,
                    productId = product._id,
                    quantity = temp,
                    price = product.price,
                    totalPrice = temp * product.price,
                    productImg = product.image
                )
                cartManager.addToCart(cartItem)?.let {
                    addToCart.visibility = View.GONE
                    subTotal.visibility = View.VISIBLE
                    subTotal.text = "$ " + it.totalPrice
                    MyToast.showToast( context,"Added to Cart")
                }
            }
        }
    }

    private fun addToCart(quantity: Int, item: Product) {

    }

    override fun getItemCount() = itemList.size
}