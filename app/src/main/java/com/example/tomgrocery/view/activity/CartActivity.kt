package com.example.tomgrocery.view.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomgrocery.R
import com.example.tomgrocery.databinding.ActivityCartBinding
import com.example.tomgrocery.model.local.AppDatabase
import com.example.tomgrocery.model.local.dao.CartDao
import com.example.tomgrocery.model.local.entity.Cart
import com.example.tomgrocery.view.adapter.CartAdapter
import com.example.tomgrocery.view.adapter.CategoryAdapter

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var appDB: AppDatabase
    private lateinit var cartDao: CartDao
    private var mState = "SHOW_MENU"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDBConnection()

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        changeActionBarTitle(supportActionBar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)

        binding.totalPrice.text = "\$%.2f".format(getTotalPrice())
        setUpCartRecyclerview()
        binding.btnCheckout.setOnClickListener {
            startActivity(Intent(applicationContext, CheckoutActivity::class.java))
        }
    }

    private fun initDBConnection() {
        appDB = AppDatabase.getInstance(baseContext)!!
        cartDao = appDB.getCartDao()
    }
    private fun setUpCartRecyclerview() {
        val cartList = cartDao.getCartItems()
        if(cartList.isEmpty()) {
            mState = "HIDE_MENU"
            invalidateOptionsMenu()
            binding.emptyCartImg.visibility = View.VISIBLE
            binding.checkoutLL.visibility = View.GONE
        }
        binding.cartRv.setHasFixedSize(true)
        binding.cartRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.cartRv.adapter = CartAdapter(this, cartList)
    }

    private fun getTotalPrice(): Float {
        return cartDao.getCartTotal()
    }

    fun updateTotalPrice() {
        binding.totalPrice.text = "\$%.2f".format(getTotalPrice())
        if(getTotalPrice() == 0.0f) {
            mState = "HIDE_MENU"
            invalidateOptionsMenu()
            binding.emptyCartImg.visibility = View.VISIBLE
            binding.checkoutLL.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.cart_menu, menu)
        val item = menu!!.findItem(R.id.cart_delete)
        item.isVisible = !mState.equals("HIDE_MENU", ignoreCase = true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cart_delete -> {
                showDeleteDialog()
                true
            }
            android.R.id.home -> {
                // todo: goto back activity from here
                val intent = Intent(this@CartActivity, DashboardActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDeleteDialog() {
        val deleteAlert = AlertDialog.Builder(this).apply {
            setTitle("Delete")
            setMessage("Do you want to Delete")
            setIcon(R.drawable.ic_delete_black_24dp)
            setPositiveButton("Delete") { dialog, _ ->
                deleteAllCartItems()
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }

    }
    private fun deleteAllCartItems() {
        cartDao.deleteAllCartItems()
        binding.cartRv.adapter = CartAdapter(this, mutableListOf())
        updateTotalPrice()
    }

    override fun onBackPressed() {
        val intent = Intent(this@CartActivity, DashboardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        finish()
    }

    private fun changeActionBarTitle(actionBar: ActionBar?) {
        actionBar?.let {
            // Create a LayoutParams for TextView
            val lp = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,  // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ) // Height of TextView
            val tv = TextView(applicationContext)
            // Apply the layout parameters to TextView widget
            tv.layoutParams = lp
            tv.gravity = Gravity.CENTER
            tv.setTypeface(null, Typeface.BOLD)
            // Set text to display in TextView
            tv.text = "Cart" // ActionBar title text
            tv.textSize = 20f

            // Set the text color of TextView to red
            // This line change the ActionBar title text color
            tv.setTextColor(resources.getColor(R.color.colorPrimaryDark))

            // Set the ActionBar display option
            actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            // Finally, set the newly created TextView as ActionBar custom view
            actionBar.customView = tv
        }
    }

    fun addToCart(cartItem: Cart): Cart? {
        cartDao.addItemToCart(cartItem)
        return cartDao.getCartItem(cartItem.productId)
    }

    fun getCartItem(productId: String): Cart? {
        return cartDao.getCartItem(productId)
    }

    fun updateCartItem(quantity: Int, productId: String): Cart? {
        val cart = cartDao.getCartItem(productId)
        cart?.let {
            val replaceCart = Cart(
                index = cart.index,
                productName = cart.productName,
                productId = cart.productId,
                quantity = quantity,
                price = cart.price,
                totalPrice = cart.price * quantity,
                productImg = cart.productImg,
                description =  cart.description
            )
            cartDao.addItemToCart(replaceCart)
            return replaceCart
        }
        return null
    }

    fun deleteCart(cart: Cart) {
        cartDao.deleteItemFromCart(cart)
    }
}