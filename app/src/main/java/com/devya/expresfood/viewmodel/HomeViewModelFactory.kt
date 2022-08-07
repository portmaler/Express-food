package com.devya.expresfood.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devya.expresfood.db.MealDataBase

class HomeViewModelFactory(
    private val mealDataBase: MealDataBase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDataBase) as T
    }
}