package com.example.pesonaliburan.front.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pesonaliburan.R
import com.example.pesonaliburan.front.fragment.content_menu.*
import com.example.pesonaliburan.sharedpreferences.SharedPreference
import kotlinx.android.synthetic.main.parent_activity_fragment_layout.*

class ParentActivityFragment:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_activity_fragment_layout)


//        setSupportActionBar(tb_destination)
//        supportActionBar!!.setDisplayShowHomeEnabled(true)
//        supportActionBar!!.setDisplayShowTitleEnabled(false)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        tb_destination.setNavigationIcon(R.drawable.ic_home_black_24dp)

    var menu2:Menu = bottomNavigation.menu
    selected_menu(menu2.getItem(0))
    bottomNavigation.setOnNavigationItemSelectedListener {
        item:MenuItem->selected_menu(item)
        false
    }

    }

    fun selected_menu(item: MenuItem){
        val sharedPref:SharedPreference = SharedPreference(this)
        item.isChecked=true
        when(item.itemId){
            R.id.destination->replace_fragment(DestinationFragment.getInstance())
            R.id.post->replace_fragment(PostFragment.getInstance())
            R.id.req-> replace_fragment(AddReqDestinationFragment.getInstance())
            R.id.myProfile->{
                if (sharedPref.getValueBoolean("status")){
                    replace_fragment(ProfileFragment.getInstance())
                }
                else{
                    replace_fragment(BeforeProfileFragment.getInstance())
                }

            }
        }
    }

    fun replace_fragment(fragment: Fragment){
        val tr =supportFragmentManager.beginTransaction()
        tr.replace(R.id.fl_container,fragment)
        tr.commit()
    }

//    override fun onSupportNavigateUp(): Boolean {
//        finish()
//        return true
//    }

    override fun onBackPressed() {
        var getBottomNavigationMenu = bottomNavigation
        var selectedItemId = getBottomNavigationMenu.selectedItemId
        if (R.id.post == selectedItemId) {
            var menu2:Menu = bottomNavigation.menu
            selected_menu(menu2.getItem(0))
        } else if (R.id.req == selectedItemId) {
            var menu2:Menu = bottomNavigation.menu
            selected_menu(menu2.getItem(1))
        }else if (R.id.myProfile == selectedItemId) {
            var menu2:Menu = bottomNavigation.menu
            selected_menu(menu2.getItem(2))
        }else{
            super.onBackPressed()
        }
    }
}