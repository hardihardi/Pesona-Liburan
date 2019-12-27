package com.example.pesonaliburan.front.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pesonaliburan.R
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponseUserModel
import kotlinx.android.synthetic.main.register_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout)
        val getAreaId = intent.extras!!.getInt("areaId")

        tv_toSigninlayout.setOnClickListener {
            var toLogin = Intent(Intent(applicationContext,LoginActivity::class.java))
            toLogin.putExtra("areaId",getAreaId)
            startActivity(toLogin)
        }

        btn_register.setOnClickListener {
            var getNameInput = et_nameUser.text.toString()
            var getUsernameInput = et_usernameLogin.text.toString()
            var getPasswordInput = et_passwordLogin.text.toString()
            var getAboutInput = et_about.text.toString()
            registerUser(getNameInput,getUsernameInput,getPasswordInput,getAreaId,getAboutInput)
        }


    }

    fun registerUser(
        nameInput: String,
        usernameInput: String,
        passwordInput: String,
        areaId: Int,
        aboutInput: String
    ) {
        if (nameInput.isEmpty()){
            Toast.makeText(applicationContext,"Nama wajib diiisi", Toast.LENGTH_SHORT).show()
        }
        else if(usernameInput.isEmpty()){
            Toast.makeText(applicationContext,"Username wajib diiisi", Toast.LENGTH_SHORT).show()
        }
        else if(passwordInput.isEmpty()) {
            Toast.makeText(applicationContext,"Password wajib diiisi", Toast.LENGTH_SHORT).show()
        }
        else{
        val apiService = Server.buildService(ApiService::class.java)
        val requestCall = apiService.register_user(nameInput,usernameInput,passwordInput,aboutInput)
        requestCall.enqueue(object :Callback<ResponseUserModel>{
            override fun onFailure(call: Call<ResponseUserModel>, t: Throwable) {
                Toast.makeText(applicationContext,"Tidak terhubung Internet", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseUserModel>, response: Response<ResponseUserModel>) {
                var responseStatus = response.body()?.status
                if (responseStatus == 200){
                    var toLogin = Intent(Intent(applicationContext,LoginActivity::class.java))
                    toLogin.putExtra("areaId",areaId)
                    startActivity(toLogin)
                    Toast.makeText(applicationContext,response.body()?.msg,Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext,response.body()?.msg,Toast.LENGTH_SHORT).show()
                }

            }

        })
        }
    }
}