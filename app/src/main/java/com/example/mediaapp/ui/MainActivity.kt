package com.example.mediaapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.mediaapp.ui.mypage.MypageFragment
import com.example.mediaapp.R
import com.example.mediaapp.SearchViewModel
import com.example.mediaapp.SearchViewModelProviderFactory
import com.example.mediaapp.data.api.VideoRepositoryImpl
import com.example.mediaapp.databinding.MainActivityBinding
import com.example.mediaapp.ui.home.HomeFragment
import com.example.mediaapp.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: MainActivityBinding
    private lateinit var currentFragment: Fragment
    private lateinit var fragmentManager: FragmentManager

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val myPageFragment = MypageFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentManager = supportFragmentManager
        currentFragment = homeFragment

        val searchRepository = VideoRepositoryImpl()
        val factory = SearchViewModelProviderFactory(searchRepository, this)
        searchViewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        supportFragmentManager.beginTransaction().replace(
            R.id.main_frame, HomeFragment(),
            TAG_HOME_FRAGMENT
        ).commit()

        val bottomNav = binding.mainNav
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.title) {
                "Home" -> {
                    switchFragment(homeFragment, TAG_HOME_FRAGMENT)
                    true
                }

                "Search" -> {
                    switchFragment(searchFragment, TAG_SEARCH_FRAGMENT)
                    true
                }

                "MyPage" -> {
                    switchFragment(myPageFragment, TAG_MY_PAGE_FRAGMENT)
                    true
                }

                else -> false
            }
        }
    }

    private fun switchFragment(fragment: Fragment, tag: String) {
        if (fragment != currentFragment) {
            fragmentManager.beginTransaction().replace(R.id.main_frame, fragment, tag).commit()
            currentFragment = fragment
        }
    }

    companion object {
        const val TAG_HOME_FRAGMENT = "home"
        const val TAG_MY_PAGE_FRAGMENT = "myPage"
        const val TAG_SEARCH_FRAGMENT = "search"
    }
}