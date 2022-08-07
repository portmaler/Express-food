package com.devya.expresfood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.devya.expresfood.R
import com.devya.expresfood.adapters.CategoryMealsAdapter
import com.devya.expresfood.databinding.ActivityCategorymealBinding
import com.devya.expresfood.fragments.HomeFragment
import com.devya.expresfood.viewmodel.CategoryMealsViewModel

class CategoryMealActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategorymealBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorymealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecycleView()

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.obserMealLiveData().observe(this, Observer { mealsList ->

            binding.tvCategoryCount.text = mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)
        })

        onItemClicked()
    }

    private fun prepareRecycleView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.tvMeals.apply {
            layoutManager = GridLayoutManager(context,2 , GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }

    private fun onItemClicked() {
        categoryMealsAdapter.onItemClick = { meal ->
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }
}