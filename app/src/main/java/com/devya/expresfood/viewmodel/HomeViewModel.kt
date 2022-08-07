package com.devya.expresfood.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devya.expresfood.db.MealDataBase
import com.devya.expresfood.pojo.*
import com.devya.expresfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDataBase: MealDataBase
): ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popolarItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoritMealLiveData = mealDataBase.mealDao().getAllMeals()
    private var bottomMealSheetLiveData = MutableLiveData<Meal>()
    private var searchMealsLiveDtaa = MutableLiveData<List<Meal>>()

    var saveStateRandomMeal : Meal? = null

    fun getRandomMeal(){
        saveStateRandomMeal?.let { randomMeal ->
            randomMealLiveData.postValue(randomMeal)
            return
        }
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                    saveStateRandomMeal = randomMeal
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItem("Seafood").enqueue(object : Callback<MealByCategoryList>{
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                if(response.body() != null){
                    popolarItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategoryList().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()!!.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)

                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
            }
        })
    }

    fun getMealById(id: String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let { meals ->
                    bottomMealSheetLiveData.postValue(meals)

                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeView:odel", t.message.toString())
            }
        })
    }
    fun searchMeala(searchQuery: String) = RetrofitInstance.api.searchMeals(searchQuery).enqueue(
        object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealsList = response.body()?.meals
                mealsList?.let { meals ->
                    searchMealsLiveDtaa.postValue(meals)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewFragment", t.message.toString())
            }
        }
    )

    fun deleteMeal(meal:Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().upsert(meal)
        }
    }

    fun observeRandomMealLiveData(): LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>>{
        return popolarItemsLiveData
    }

    fun observeCategoriesLiveDat(): LiveData<List<Category>>{
        return categoriesLiveData
    }

    fun observeFavoriteMealsLiveDta():LiveData<List<Meal>>{
        return favoritMealLiveData
    }

    fun observeBottomSheetmeal() : LiveData<Meal>{
        return bottomMealSheetLiveData
    }

    fun observeSearchMealsLiveDta():LiveData<List<Meal>>{
        return searchMealsLiveDtaa
    }
}