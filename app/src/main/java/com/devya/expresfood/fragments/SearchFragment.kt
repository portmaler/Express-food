package com.devya.expresfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devya.expresfood.R
import com.devya.expresfood.activities.MainActivity
import com.devya.expresfood.adapters.MealsAdapter
import com.devya.expresfood.databinding.FragmentSearchBinding
import com.devya.expresfood.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchRecycleViewAdapter: MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecycleView()

        binding.icSearch.setOnClickListener {
            searchmeals()
        }

        observeSearchMealLiveData()

        var searchJob: Job? = null
        binding.edSearch.addTextChangedListener { searchQuery ->
            searchJob?.cancel()

                searchJob = lifecycleScope.launch {
                    delay(1000)
                    viewModel.searchMeala(searchQuery.toString())
                }
        }
    }

    private fun observeSearchMealLiveData() {
        viewModel.observeSearchMealsLiveDta().observe(viewLifecycleOwner, Observer { meals ->
            searchRecycleViewAdapter.differ.submitList(meals)
        })
    }

    private fun searchmeals() {
        val searchQuery = binding.edSearch.text.toString()
        if(searchQuery.isNotEmpty()){
            viewModel.searchMeala(searchQuery)
        }
    }

    private fun prepareRecycleView() {
        searchRecycleViewAdapter = MealsAdapter()
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(context, 2 , GridLayoutManager.VERTICAL, false)
            adapter = searchRecycleViewAdapter
        }
    }

}