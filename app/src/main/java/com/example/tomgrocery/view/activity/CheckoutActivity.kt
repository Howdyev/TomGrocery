package com.example.tomgrocery.view.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.tomgrocery.R
import com.example.tomgrocery.databinding.ActivityCheckoutBinding
import com.example.tomgrocery.model.local.AppDatabase
import com.example.tomgrocery.model.local.dao.AddressDao
import com.example.tomgrocery.model.local.dao.CartDao
import com.example.tomgrocery.model.local.entity.Cart
import com.example.tomgrocery.model.remote.dto.Address
import com.example.tomgrocery.view.fragment.AddressFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var appDB: AppDatabase
    private lateinit var cartDao: CartDao
    private lateinit var addressDao: AddressDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDBConnection()

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        changeActionBarTitle(supportActionBar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
            .replace(R.id.content_frame, AddressFragment())
            .commit()
    }

    private fun initDBConnection() {
        appDB = AppDatabase.getInstance(baseContext)!!
        cartDao = appDB.getCartDao()
        addressDao = appDB.getAdressDao()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this@CheckoutActivity, CartActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(applicationContext, DashboardActivity::class.java))
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
            //tv.setTypeface(null, Typeface.BOLD);
            // Set text to display in TextView
            tv.text = "Checkout" // ActionBar title text
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

    fun getTotalPrice(): Float {
        return cartDao.getCartTotal()
    }

    fun getAllCartItems(): MutableList<Cart> {
        return cartDao.getCartItems()
    }

    fun getAllAddressItems(): List<Address> {
        return addressDao.getAllAddresses()
    }

    fun clearCarts() {
        return cartDao.deleteAllCartItems()
    }
}