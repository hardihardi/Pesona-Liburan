package com.example.pesonaliburan.front.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pesonaliburan.R
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponseUserModel
import com.example.pesonaliburan.sharedpreferences.SharedPreference
import kotlinx.android.synthetic.main.login_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        val sharedPref = SharedPreference(this)
        val getAreaId = intent.extras!!.getInt("areaId")


        tv_toSignuplayout.setOnClickListener {
            var intent = Intent(Intent(this,RegisterActivity::class.java))
            intent.putExtra("areaId",getAreaId)
            startActivity(intent)
        }


        btn_login.setOnClickListener {
            val getUsernameInput = et_usernameLogin.text.toString()
            val getPasswordInput = et_passwordLogin.text.toString()
            //Method
            login_user(getUsernameInput,getPasswordInput,sharedPref,getAreaId)
        }
    }

    private fun login_user(usernameInput: String, passwordInput: String, sharedPref: SharedPreference, areaId: Int) {
        val apiService = Server.buildService(ApiService::class.java)
        val callRequest = apiService.login_user(usernameInput,passwordInput)
        callRequest.enqueue(object : Callback<ResponseUserModel>{

            override fun onResponse(call: Call<ResponseUserModel>, response: Response<ResponseUserModel>) {
                var responseStatus = response.body()?.status
                var responseData = response.body()?.data
                if (responseStatus == 200) {
                    responseData?.forEach {
                        sharedPref.save("user_id", it.user_id!!)
                        sharedPref.save("name_user", it.name_user!!)
                        sharedPref.save("username", it.username!!)
                        sharedPref.save("password", it.password!!)
                        sharedPref.save("about_user", it.about_user!!)
                        sharedPref.save("status", true)
                    }
                    val toParentActivityFragment = Intent(applicationContext,ParentActivityFragment::class.java)
                    toParentActivityFragment.putExtra("areaId",areaId)
                    startActivity(toParentActivityFragment)
                    Toast.makeText(applicationContext,response.body()?.msg,Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext,response.body()?.msg,Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseUserModel>, t: Throwable) {
                Toast.makeText(applicationContext,"Maaf ada kesalahan dalam sistem kami",Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

        })
    }

    override fun onBackPressed() {
        exitProcess(0)
    }
}