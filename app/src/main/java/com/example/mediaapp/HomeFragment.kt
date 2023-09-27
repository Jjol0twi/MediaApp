package com.example.mediaapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.databinding.HomeFragmentBinding
import com.example.mediaapp.ui.adapter.HomeRecyclerViewAdapter


class HomeFragment : Fragment() {

    private var _binding:HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel = (activity as MainActivity).searchViewModel


        setupRecyclerView()
        searchImages()

        searchViewModel.searchResult.observe(viewLifecycleOwner){ response ->
            val result:List<Item> = response.items

            homeRecyclerViewAdapter.submitList(result)
            Log.d("TAG", "submitList? : ${homeRecyclerViewAdapter.submitList(result)}")

        }




    }

    private fun searchImages() {

        binding.searchFragBtnSearch.setOnClickListener {
            val searchBar = binding.searchFragEditSearch
            if (searchBar.text.isNotEmpty()) {
                searchBar.text?.let {
                    val query = it.toString().trim()
                    if (query.isNotEmpty()) {
                        searchViewModel.searchYoutube(query)
                    }
                }
            }
        }
    }


    private fun setupRecyclerView(){
        homeRecyclerViewAdapter = HomeRecyclerViewAdapter()
        binding.homeRcvCategoryList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeRecyclerViewAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}