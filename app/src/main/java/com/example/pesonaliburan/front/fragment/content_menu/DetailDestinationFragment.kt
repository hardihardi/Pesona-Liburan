package com.example.pesonaliburan.front.fragment.content_menu


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pesonaliburan.R

/**
 * A simple [Fragment] subclass.
 */
class DetailDestinationFragment : Fragment() {

    companion object{
        fun getInstance():DetailDestinationFragment=DetailDestinationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_destination, container, false)
    }


}
