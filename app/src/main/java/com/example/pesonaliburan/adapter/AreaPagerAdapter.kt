package com.example.pesonaliburan.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pesonaliburan.front.fragment.EastIndonesiaFragment
import com.example.pesonaliburan.front.fragment.MiddleIndonesiaFragment
import com.example.pesonaliburan.front.fragment.WestIndonesiaFragment

class AreaPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages= listOf(
            WestIndonesiaFragment(),
            MiddleIndonesiaFragment(),
            EastIndonesiaFragment()
    )

    override fun getItem(position: Int): Fragment {
      return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

}