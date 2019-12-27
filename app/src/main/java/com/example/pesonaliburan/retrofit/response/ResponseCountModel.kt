package com.example.pesonaliburan.retrofit.response

import com.example.pesonaliburan.retrofit.model.CountModel

data class ResponseCountModel (val status:Int, val data:List<CountModel>,val msg:String)