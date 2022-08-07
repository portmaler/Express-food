package com.devya.expresfood.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.devya.expresfood.R
import com.devya.expresfood.activities.MainActivity
import com.devya.expresfood.activities.MealActivity
import com.devya.expresfood.databinding.FragmentMealBottomSheetBinding
import com.devya.expresfood.fragments.HomeFragment
import com.devya.expresfood.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val ARG_PARAM1 = "param1"

class MealBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel
    private var mealID: String? = null
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { 
            mealID = it.getString(ARG_PARAM1)
        }

        viewModel = (activity as MainActivity).viewModel
        
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealID?.let { viewModel.getMealById(it) }
        obserBottomMealSheet()
        onBottomSheetDiaclogClick()
    }

    private fun onBottomSheetDiaclogClick() {
        binding.bottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null){
                val intent = Intent(activity,MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID, mealID)
                    putExtra(HomeFragment.MEAL_NAME, mealName)
                    putExtra(HomeFragment.MEAL_THUMB, mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private var mealName: String? = null
    private var mealThumb: String? = null
    private fun obserBottomMealSheet() {
        viewModel.observeBottomSheetmeal().observe(viewLifecycleOwner, Observer { meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgBottomSheet)
            binding.tvMealCategory.text = meal.strCategory
            binding.tvMealNameInBtmsheet.text = meal.strMeal
            binding.tvMealCountry.text = meal.strArea

            mealName = meal.strMeal
            mealThumb = meal.strMealThumb
        })
    }

    companion object {
     
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
               
                }
            }
    }
}