package com.example.pesonaliburan.retrofit.response

import com.example.pesonaliburan.retrofit.model.PostModel

data class ResponsePostModel (val status:Int, val data:List<PostModel>, val msg:String)