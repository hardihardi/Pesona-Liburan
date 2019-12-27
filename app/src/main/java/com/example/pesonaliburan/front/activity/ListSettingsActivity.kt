package com.example.pesonaliburan.front.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pesonaliburan.R
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponseLogoutUser
import com.example.pesonaliburan.sharedpreferences.SharedPreference
import kotlinx.android.synthetic.main.list_settings_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListSettingsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_settings_activity)

        val sharedPref = SharedPreference(this)
        val getAreaId = intent.extras!!.getInt("areaId")

        tv_editProfile.setOnClickListener {
            Toast.makeText(applicationContext,"Maaf fitur ubah profil dalam perbaikan",Toast.LENGTH_SHORT).show()
        }

        tv_logout.setOnClickListener {
            var getUserId = sharedPref.getValueInt("user_id")
            logout(getUserId,getAreaId)
        }
    }

    fun logout(userId: Int, areaId: Int) {
        val sharedPref:SharedPreference= SharedPreference(this)
        val apiService =Server.buildService(ApiService::class.java)
        val requestCall = apiService.logout(userId)
        requestCall.enqueue(object: Callback<ResponseLogoutUser> {
            override fun onResponse(call: Call<ResponseLogoutUser>, response: Response<ResponseLogoutUser>) {
                var statusResponse = response.body()!!.status
                if (statusResponse == 200){
                    sharedPref.clearSharedPrefrences()
                    var toLogin = Intent(Intent(applicationContext,LoginActivity::class.java))
                    toLogin.putExtra("areaId",areaId)
                    startActivity(toLogin)
                    finish()
                    Toast.makeText(applicationContext,response.body()!!.msg,Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext,response.body()!!.msg,Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseLogoutUser>, t: Throwable) {
                Toast.makeText(applicationContext,"Maaf ada kesalahan dalam server kami",Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

        })
    }
}