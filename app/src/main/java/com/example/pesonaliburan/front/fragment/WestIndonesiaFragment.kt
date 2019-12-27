package com.example.pesonaliburan.front.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pesonaliburan.R
import com.example.pesonaliburan.front.activity.ParentActivityFragment
import kotlinx.android.synthetic.main.fragment_west_indonesia.*
import kotlinx.android.synthetic.main.fragment_west_indonesia.view.*

/**
 * A simple [Fragment] subclass.
 */
class WestIndonesiaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_west_indonesia, container, false)
        view.btn_westArea.setOnClickListener {
            val toParentActivityFragment = Intent(activity,ParentActivityFragment::class.java)
            toParentActivityFragment.putExtra("areaId",1)
            activity?.startActivity(toParentActivityFragment)
        }
        return view
    }


}
