package com.devya.expresfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devya.expresfood.databinding.CategoryItemBinding
import com.devya.expresfood.pojo.Category

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categoriesList = ArrayList<Category>()
    var onItemClick : ((Category) -> Unit)? = null


    inner class CategoryViewHolder(val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)

    fun setCategoryList(categoriesList:List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoriesList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}