package com.example.tomgrocery.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tomgrocery.R
import com.example.tomgrocery.constants.Constants.BASE_IMAGE_URL
import com.example.tomgrocery.databinding.RowCategoryBinding
import com.example.tomgrocery.model.remote.dto.Category
import com.example.tomgrocery.view.activity.DashboardActivity
import com.example.tomgrocery.viewmodel.DashboardViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.internal.managers.ViewComponentManager
import java.lang.Exception


class CategoryAdapter(
    private val context: Context,
    private val categoryList: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private lateinit var binding: RowCategoryBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        binding = RowCategoryBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.apply {
            val category = categoryList[position]
            bind(category)
            itemView.setOnClickListener {
                DashboardViewModel.staticCategoryId = category.catId
                val mContext = if (context is ViewComponentManager.FragmentContextWrapper)
                    context.baseContext
                else
                    context
                val dashboardActivity = mContext as DashboardActivity

                dashboardActivity?.let {
                    it.displaySelectedScreen(R.id.nav_products)
                }
            }
        }
    }

    override fun getItemCount() = categoryList.size

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(category: Category) {
            try {
                Picasso
                    .get()
                    .load(BASE_IMAGE_URL + category.catImage)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(binding.categoryImage)
                binding.progressbar.visibility = View.GONE

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                binding.progressbar.visibility = View.GONE
            }
            binding.categoryTitle.text = category.catName
        }
    }
}