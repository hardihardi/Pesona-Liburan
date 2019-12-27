package com.example.pesonaliburan.retrofit.response

import com.example.pesonaliburan.retrofit.model.ReqDestinationModel

data class ResponseReqDestinationModel (val status:Int, val data:List<ReqDestinationModel>, val msg:String)