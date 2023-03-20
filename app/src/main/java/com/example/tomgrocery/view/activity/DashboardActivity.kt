package com.example.tomgrocery.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.room.PrimaryKey
import com.example.tomgrocery.R
import com.example.tomgrocery.constants.Constants.DASHBOARD_CATEGORY
import com.example.tomgrocery.constants.Constants.DASHBOARD_HOME
import com.example.tomgrocery.databinding.ActivityDashboardBinding
import com.example.tomgrocery.databinding.NavHeaderMainBinding
import com.example.tomgrocery.model.local.AppDatabase
import com.example.tomgrocery.model.local.dao.CartDao
import com.example.tomgrocery.model.local.entity.Cart
import com.example.tomgrocery.model.remote.dto.Product
import com.example.tomgrocery.util.localstorage.LocalStorage
import com.example.tomgrocery.view.fragment.CategoryFragment
import com.example.tomgrocery.view.fragment.HomeFragment
import com.example.tomgrocery.view.fragment.ProductsFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var localStorage: LocalStorage
    private lateinit var appDB: AppDatabase
    private lateinit var cartDao: CartDao
    var cartList = listOf<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        localStorage = LocalStorage(applicationContext)
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDBConnection()

        setSupportActionBar(binding.appbarMain.toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appbarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.setDrawerListener(toggle)
        toggle.syncState()

        binding.appbarMain.fab.visibility = View.GONE

        binding.navHeaderMain.setNavigationItemSelectedListener(this)
        val headerView = binding.navHeaderMain.getHeaderView(0)
        val headerBinding = NavHeaderMainBinding.bind(headerView)
        headerBinding.navHeaderName.text = localStorage.getUserName()
        binding.navFooterMain.footerText.setOnClickListener {
            localStorage.logoutUser()
            startActivity(Intent(this, LoginRegisterActivity::class.java))
            finish()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }
        displaySelectedScreen(R.id.nav_home)
    }

    private fun initDBConnection() {
        appDB = AppDatabase.getInstance(baseContext)!!
        cartDao = appDB.getCartDao()
    }

    fun displaySelectedScreen(itemId: Int) {

        //creating fragment object
        var fragment: Fragment? = null
        when (itemId) {
            R.id.nav_home -> fragment = HomeFragment()
            R.id.nav_category -> fragment = CategoryFragment()
            R.id.nav_products -> fragment = ProductsFragment()
            R.id.nav_my_cart -> {
                startActivity(Intent(applicationContext, CartActivity::class.java))
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }
        }

        //replacing the fragment
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                .replace(R.id.content_frame, fragment)
                .commit()
        }
        val drawer: DrawerLayout = binding.drawerLayout
        drawer.closeDrawer(GravityCompat.START)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        displaySelectedScreen(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displaySelectedScreen(item.itemId)
        return true
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
}