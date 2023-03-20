package com.example.tomgrocery.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.tomgrocery.R

class ViewPagerAdapter(
    private val context: Context
): PagerAdapter() {
    private val images: List<Int> = listOf(
        R.drawable.welcome_slider1,
        R.drawable.welcome_slider2,
        R.drawable.welcome_slider3
    )
    private val title = arrayOf("Purchase Online", "Choose and Checkout", "Get Your Order")
    private val description = arrayOf(
        "",
        "",
        ""
    )

    override fun getCount() = images.size


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.item_slider, null)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(images[position])
        val titleTV = view.findViewById<TextView>(R.id.title)
        titleTV.text = title[position]
        val desc = view.findViewById<TextView>(R.id.description)
        desc.text = description[position]

        val vp = container as ViewPager
        vp.addView(view, 0)
        return view;
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }

}