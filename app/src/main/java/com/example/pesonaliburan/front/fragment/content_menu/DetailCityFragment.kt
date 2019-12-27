package com.example.pesonaliburan.front.fragment.content_menu


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager

import com.example.pesonaliburan.R
import com.example.pesonaliburan.adapter.SubDestinationAdapter
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponseDestinationModel
import com.example.pesonaliburan.style.GridItemDecoration
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_city.*
import kotlinx.android.synthetic.main.fragment_detail_city.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class DetailCityFragment : Fragment() {

    lateinit var SubDestinationAdapter:SubDestinationAdapter

    companion object{
        fun get_instance():DetailCityFragment=DetailCityFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_city, container, false)
        val bundle = arguments
        if (bundle != null){
            view.tv_nameShowMoreDestination.text=bundle.getString("city_name")
            view.tv_descCity.text=bundle.getString("city_desc")
            Picasso.with(context).load("https://apipesonaliburan.000webhostapp.com/img/"+bundle.getString("city_img")).into(view.iv_detailCity)
            view.tv_titleCity.text=bundle.getString("city_name")
        }
        val cityId = bundle!!.getInt("city_id")
        val cityName = bundle.getString("city_name")
        //Recyler View
        SubDestinationAdapter=SubDestinationAdapter(activity!!)
        view.rc_subDestination.layoutManager = GridLayoutManager(activity,2)
        view.rc_subDestination.addItemDecoration(GridItemDecoration(2,20,true))
        view.rc_subDestination.adapter=SubDestinationAdapter

        //To Show More Destiantion
        view.btn_showMoreDestination.setOnClickListener {
            val bundle2 = Bundle()
            bundle2.putInt("city_id",cityId)
            bundle2.putString("city_name",cityName)
            val ShowMoreDestinationFragment = ShowMoreDestinationFragment()
            ShowMoreDestinationFragment.arguments = bundle2
            replace_fragment(ShowMoreDestinationFragment)
        }


        //Method
        get_destination(cityId);

        return view
    }

    private fun replace_fragment(fragment: Fragment){
        val tr = fragmentManager!!.beginTransaction()
        tr.replace(R.id.fl_container,fragment)
        tr.addToBackStack("fragment")
        tr.commit()
    }

    private fun get_destination(cityId: Int) {
        var apiService = Server.buildService(ApiService::class.java)
        var callRequest = apiService.get_destination(cityId)
        callRequest.enqueue(object :Callback<ResponseDestinationModel>{
            override fun onResponse(call: Call<ResponseDestinationModel>, response: Response<ResponseDestinationModel>) {
                var statusResponse = response.body()?.status
                if (statusResponse == 200){
                    SubDestinationAdapter.setSubDestinationList(response.body()!!.data)
                    pb_subDestination?.isVisible=false
                }
                else{
                    Toast.makeText(context, response.body()?.msg,Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseDestinationModel>, t: Throwable) {
                Toast.makeText(context,"Maaf ada kesalahan pada system kami",Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

        })
    }


}

