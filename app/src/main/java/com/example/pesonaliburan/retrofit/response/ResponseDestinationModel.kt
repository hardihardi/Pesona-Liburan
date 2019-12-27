package com.example.pesonaliburan.retrofit.response

import com.example.pesonaliburan.retrofit.model.DestinationModel

data class ResponseDestinationModel (val status:Int, val data:List<DestinationModel>, val msg:String)