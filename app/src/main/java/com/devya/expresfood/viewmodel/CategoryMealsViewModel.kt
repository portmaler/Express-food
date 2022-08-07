package com.devya.expresfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devya.expresfood.pojo.MealByCategoryList
import com.devya.expresfood.pojo.MealsByCategory
import com.devya.expresfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel: ViewModel() {

    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealByCategoryList>{
            override fun onResponse(
                call: Call<MealByCategoryList>,
                response: Response<MealByCategoryList>
            ) {
               response.body()?.let { mealsList ->
                   mealsLiveData.postValue(mealsList.meals)

               }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.e("CategoryMealsViewModel", t.message.toString())
            }
        })
    }

    fun obserMealLiveData(): LiveData<List<MealsByCategory>>{
        return mealsLiveData
    }
}