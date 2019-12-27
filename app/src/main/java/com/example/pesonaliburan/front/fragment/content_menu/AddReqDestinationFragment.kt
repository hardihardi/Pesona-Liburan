package com.example.pesonaliburan.front.fragment.content_menu


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.pesonaliburan.R
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponseReqDestinationModel
import kotlinx.android.synthetic.main.fragment_add_req_destination.*
import kotlinx.android.synthetic.main.fragment_add_req_destination.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class AddReqDestinationFragment : Fragment() {

    companion object{
        fun getInstance():AddReqDestinationFragment=AddReqDestinationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_req_destination, container, false)

        view.btn_sendReq.setOnClickListener {
            val getNameCity = et_nameCity.text.toString()
            val getDestination = et_nameDestination.text.toString()
            val getDescDes = et_descDestination.text.toString()
            sendReqDes(getNameCity,getDestination,getDescDes)
        }
        return view
    }

    fun sendReqDes(nameCity: String, destination: String, descDes: String) {
        val apiService = Server.buildService(ApiService::class.java)
        val requestCall = apiService.addReqDes(nameCity,destination,descDes)
        requestCall.enqueue(object :Callback<ResponseReqDestinationModel>{
            override fun onFailure(call: Call<ResponseReqDestinationModel>, t: Throwable) {
                Toast.makeText(activity,"Belum terhubung Internet", Toast.LENGTH_SHORT).show()
               t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseReqDestinationModel>, response: Response<ResponseReqDestinationModel>) {
                var statusResponse = response.body()?.status
                if (statusResponse == 200){
                    et_nameCity.text.clear()
                    et_nameDestination.text.clear()
                    et_descDestination.text.clear()
                    Toast.makeText(activity,"Request berhasil terkirim",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(activity,"Request gagal terkirim",Toast.LENGTH_SHORT).show()
                }
            }

        })
    }


}
