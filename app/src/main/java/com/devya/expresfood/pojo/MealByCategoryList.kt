package com.devya.expresfood.pojo


import com.google.gson.annotations.SerializedName

data class MealByCategoryList(
    @SerializedName("meals")
    val meals: List<MealsByCategory>
)