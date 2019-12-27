package com.example.pesonaliburan.front.fragment.content_menu


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.pesonaliburan.R
import com.example.pesonaliburan.adapter.SubDestinationImgAdapter
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponseImgSubDestinationModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_sub_destination.*
import kotlinx.android.synthetic.main.fragment_detail_sub_destination.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class DetailSubDestinationFragment : Fragment() {

    lateinit var SubDestinationImgAdapter:SubDestinationImgAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_sub_destination, container, false)

        //GET DATA
        val bundle = arguments
        var getDestination_id:Int = bundle!!.getInt("destination_id")
        view.tv_nameSubDestination.text=bundle.getString("destination_name")
        view.tv_descSubDestination.text=bundle.getString("destination_desc")
        Picasso.with(context).load("https://apipesonaliburan.000webhostapp.com/img/"+bundle.getString("destination_img")).into(view.iv_SubdetailDestination)


        SubDestinationImgAdapter= SubDestinationImgAdapter(activity!!)
        view.rc_imgSubDestination.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        view.rc_imgSubDestination.adapter=SubDestinationImgAdapter

        getImgSubDestination(getDestination_id)

        return view
    }

    fun getImgSubDestination(destinationId: Int) {
        val apiService = Server.buildService(ApiService::class.java)
        val requestCall = apiService.get_img_Subdes(destinationId)
        requestCall.enqueue(object :Callback<ResponseImgSubDestinationModel>{
            override fun onResponse(call: Call<ResponseImgSubDestinationModel>, response: Response<ResponseImgSubDestinationModel>) {
                val statusResponse = response.body()!!.status
                if (statusResponse == 200){
                   SubDestinationImgAdapter.setSubImgDestinationList(response.body()!!.data)
                    pb_subDestination.isVisible=false
                }
                else{
                    Toast.makeText(context,"Gagal memuat", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseImgSubDestinationModel>, t: Throwable) {
                Toast.makeText(context,"Internet tidak terhubung", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }


}
