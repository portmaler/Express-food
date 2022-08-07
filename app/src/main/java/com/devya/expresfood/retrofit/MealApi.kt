package com.devya.expresfood.retrofit

import com.devya.expresfood.pojo.CategoryList
import com.devya.expresfood.pojo.MealByCategoryList
import com.devya.expresfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id: String): Call<MealList>

    @GET("filter.php")
    fun getPopularItem(@Query("c") categoryName: String ): Call<MealByCategoryList>

    @GET("categories.php")
    fun getCategoryList(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String) : Call<MealByCategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") searchQuery: String): Call<MealList>
}