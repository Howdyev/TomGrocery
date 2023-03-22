package com.example.tomgrocery.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.tomgrocery.R
import com.example.tomgrocery.databinding.ActivityDashboardBinding
import com.example.tomgrocery.databinding.NavHeaderMainBinding
import com.example.tomgrocery.model.local.AppDatabase
import com.example.tomgrocery.model.local.dao.CartDao
import com.example.tomgrocery.model.local.entity.Cart
import com.example.tomgrocery.model.remote.dto.Product
import com.example.tomgrocery.util.CartBadgeConverter
import com.example.tomgrocery.util.localstorage.LocalStorage
import com.example.tomgrocery.view.fragment.CategoryFragment
import com.example.tomgrocery.view.fragment.HomeFragment
import com.example.tomgrocery.view.fragment.MyOrderFragment
import com.example.tomgrocery.view.fragment.ProductsFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var localStorage: LocalStorage
    private lateinit var appDB: AppDatabase
    private lateinit var cartDao: CartDao
    private var cartMenuItem: MenuItem? = null
    var cartList = listOf<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        localStorage = LocalStorage(applicationContext)
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDBConnection()

        setSupportActionBar(binding.appbarMain.toolbar)
        centerToolbarTitle(binding.appbarMain.toolbar)
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

    private fun centerToolbarTitle(toolbar: Toolbar) {
        val title = toolbar.title
        val outViews = ArrayList<View>(1)
        toolbar.findViewsWithText(outViews, title, View.FIND_VIEWS_WITH_TEXT)
        if (!outViews.isEmpty()) {
            val titleView = outViews[0] as TextView
            titleView.gravity = Gravity.CENTER
            titleView.setTextColor(Color.parseColor("#f9a825"))
            val layoutParams = titleView.layoutParams as Toolbar.LayoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            toolbar.requestLayout()
            //also you can use titleView for changing font: titleView.setTypeface(Typeface);
        }
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
            R.id.nav_my_order -> fragment = MyOrderFragment()
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
        menu?.let { letMenu ->
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.main, menu)
            cartMenuItem = menu.findItem(R.id.cart_action)
            cartMenuItem?.icon = CartBadgeConverter.convertLayoutToImage(
                this@DashboardActivity,
                getCartCount(),
                R.drawable.ic_shopping_basket
            )
            val searchItem = menu.findItem(R.id.action_search)
            var searchView: SearchView? = null
            if (searchItem != null) {
                searchView = searchItem.actionView as SearchView?
            }
            searchView?.let { letSearchView->
                searchView.queryHint = "Search Here..."
                val searchBox = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
                searchBox.setTextColor(Color.BLACK)
                searchBox.setHintTextColor(Color.GRAY)
                searchView.setIconifiedByDefault(true)
                val searchClose =
                    searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
                searchClose.setImageResource(R.drawable.ic_close_black_24dp)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.cart_action) {
            startActivity(Intent(applicationContext, CartActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displaySelectedScreen(item.itemId)
        return true
    }

    fun addToCart(cartItem: Cart): Cart? {
        cartDao.addItemToCart(cartItem)
        cartMenuItem?.icon = CartBadgeConverter.convertLayoutToImage(
            this@DashboardActivity,
            getCartCount(),
            R.drawable.ic_shopping_basket
        )
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

    private fun getCartCount(): Int {
        return return cartDao.getCount()
    }
}