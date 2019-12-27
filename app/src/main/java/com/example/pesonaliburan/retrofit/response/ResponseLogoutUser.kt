package com.example.pesonaliburan.retrofit.response

import com.example.pesonaliburan.retrofit.model.UserModel

data class ResponseLogoutUser (val status:Int, val data:List<UserModel>, val msg:String)