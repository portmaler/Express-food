package com.devya.expresfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.devya.expresfood.R
import com.devya.expresfood.activities.MainActivity
import com.devya.expresfood.adapters.CategoriesAdapter
import com.devya.expresfood.databinding.FragmentCategoriesBinding
import com.devya.expresfood.viewmodel.HomeViewModel


class CategoriesFragment : Fragment() {

    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var catogoriesAdapter: CategoriesAdapter
    private lateinit var viewModel: HomeViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecycleView()
        observeCategories()
    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveDat().observe(viewLifecycleOwner, Observer{categories ->
            catogoriesAdapter.setCategoryList(categories)

        })
    }

    private fun prepareRecycleView() {
        catogoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = catogoriesAdapter
        }
    }
}