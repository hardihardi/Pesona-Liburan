package com.example.pesonaliburan.front.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pesonaliburan.R
import com.example.pesonaliburan.front.activity.ParentActivityFragment
import kotlinx.android.synthetic.main.fragment_east_indonesia.view.*
import kotlinx.android.synthetic.main.fragment_west_indonesia.view.*

/**
 * A simple [Fragment] subclass.
 */
class EastIndonesiaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_east_indonesia,container,false)
        view.btn_eastArea.setOnClickListener {
            val toParentActivityFragment = Intent(activity, ParentActivityFragment::class.java)
            toParentActivityFragment.putExtra("areaId",3)
            activity?.startActivity(toParentActivityFragment)
        }
        return view
    }


}
