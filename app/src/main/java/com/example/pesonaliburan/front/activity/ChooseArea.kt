package com.example.pesonaliburan.front.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pesonaliburan.R
import com.example.pesonaliburan.adapter.AreaPagerAdapter
import kotlinx.android.synthetic.main.choose_area_layout.*
import kotlin.system.exitProcess

class ChooseArea : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_area_layout)

        vp_main.adapter = AreaPagerAdapter(supportFragmentManager)

    }

    override fun onBackPressed() {
        exitProcess(0)
    }
}
