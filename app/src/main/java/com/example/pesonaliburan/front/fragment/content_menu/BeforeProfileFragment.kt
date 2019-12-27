package com.example.pesonaliburan.front.fragment.content_menu


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pesonaliburan.R
import com.example.pesonaliburan.front.activity.LoginActivity
import com.example.pesonaliburan.style.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_before_profile.*
import kotlinx.android.synthetic.main.fragment_before_profile.view.*

/**
 * A simple [Fragment] subclass.
 */
class BeforeProfileFragment : Fragment() {

    companion object{
        fun getInstance():BeforeProfileFragment = BeforeProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_before_profile, container, false)
        val getAreaId = activity!!.intent.extras!!.getInt("areaId")
        Picasso.with(context).load("https://apipesonaliburan.000webhostapp.com/img/"+"default-profile.png").transform(CircleTransform()).into(view.iv_userImg)
        view.btn_toLogin.setOnClickListener {
            var intent = Intent(Intent(context,LoginActivity::class.java))
            intent.putExtra("areaId",getAreaId)
            activity!!.startActivity(intent)
        }
        return view
    }


}
