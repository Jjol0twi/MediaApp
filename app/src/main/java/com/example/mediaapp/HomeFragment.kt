package com.example.mediaapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaapp.data.model.channel.ChItem
import com.example.mediaapp.util.CategoryId
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.databinding.HomeFragmentBinding
import com.example.mediaapp.ui.adapter.ChannelItemClick
import com.example.mediaapp.ui.adapter.HomeCategoryRcvViewAdapter
import com.example.mediaapp.ui.adapter.HomeChannelRcvAdapter
import com.example.mediaapp.ui.adapter.HomeTrendingRcvAdapter
import com.example.mediaapp.ui.adapter.ItemClick


class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var homeCategoryRcvViewAdapter: HomeCategoryRcvViewAdapter
    private lateinit var homeTrendingRcvViewAdapter: HomeTrendingRcvAdapter
    private lateinit var homeChannelRcvAdapter: HomeChannelRcvAdapter

    private var isUserScrolling = false

    private var scrollHandler = Handler(Looper.getMainLooper())
    private val scrollRunnable = object : Runnable {
        override fun run() {
            autoScroll()
            scrollHandler.postDelayed(this,5000)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel = (activity as MainActivity).searchViewModel


        setupRecyclerView()
        searchCategory()
        searchViewModel.searchYoutube("1")
        searchViewModel.searchChannels("tv")
        searchViewModel.searchTrending()
        searchViewModel.trendingResult.observe(viewLifecycleOwner){ response ->
            val result:List<Item> = response.items

            homeTrendingRcvViewAdapter.submitList(result)
        }

        searchViewModel.searchResult.observe(viewLifecycleOwner){ response ->
            val result:List<Item> = response.items

            homeCategoryRcvViewAdapter.submitList(result)
            Log.d("TAG", "submitList? : ${homeCategoryRcvViewAdapter.submitList(result)}")

        }

        searchViewModel.channelResult.observe(viewLifecycleOwner){ response ->
            val result:List<ChItem> = response.chItems

            homeChannelRcvAdapter.submitList(result)
        }

        scrollHandler.postDelayed(scrollRunnable,5000)
    }

    private fun searchCategory() {

        binding.homeSpnCategorySelect.setOnSpinnerItemSelectedListener<String> { _, _, _, query ->
            searchViewModel.searchYoutube(CategoryId.categoryMap[query] ?: "1")
            searchViewModel.searchChannels(query)
        }
    }


    private fun setupRecyclerView() {
        val trendingSnapHelper = LinearSnapHelper()
        trendingSnapHelper.attachToRecyclerView(binding.homeRcvTrendingList)
        homeCategoryRcvViewAdapter = HomeCategoryRcvViewAdapter(object : ItemClick {
            override fun onClick(item: Item) {
                val detail = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable("Video_data", item)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, detail)
                    .addToBackStack(null)
                    .commit()
            }
        })
        binding.homeRcvCategoryList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeCategoryRcvViewAdapter
        }

        homeTrendingRcvViewAdapter = HomeTrendingRcvAdapter(object : ItemClick {
            override fun onClick(item: Item) {
                val detail = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable("Video_data", item)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, detail)
                    .addToBackStack(null)
                    .commit()
            }
        })

        binding.homeRcvTrendingList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeTrendingRcvViewAdapter
            addOnScrollListener(trendingRcvListener)
        }

        homeChannelRcvAdapter = HomeChannelRcvAdapter(object : ChannelItemClick {
            override fun onClick(item: ChItem) {
                Log.d("jina", "homeChannelAdapter: onClick $item")
                val detail = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable("Video_data", item)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, detail)
                    .addToBackStack(null)
                    .commit()
            }
        })

        binding.homeRcvChannelList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeChannelRcvAdapter
        }
    }

    private val trendingRcvListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            when (newState) {
                RecyclerView.SCROLL_STATE_DRAGGING -> {
                    isUserScrolling = true
                }
                RecyclerView.SCROLL_STATE_IDLE -> {
                    if (isUserScrolling){
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                        val itemCount = recyclerView.adapter?.itemCount ?: 0

                        if (lastVisibleItemPosition == itemCount -1 ){
                            recyclerView.scrollToPosition(0)
                        }
                    }
                    isUserScrolling = false
                }
            }
        }
    }
    private fun autoScroll() {
        val layoutManager = binding.homeRcvTrendingList.layoutManager as LinearLayoutManager
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val itemCount = homeTrendingRcvViewAdapter.itemCount

        if (lastVisibleItemPosition < itemCount -1){
            binding.homeRcvTrendingList.smoothScrollToPosition(lastVisibleItemPosition + 1)
        } else {
            binding.homeRcvTrendingList.scrollToPosition(0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        scrollHandler.removeCallbacksAndMessages(scrollRunnable)
    }
}