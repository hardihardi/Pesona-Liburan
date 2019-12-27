package com.example.pesonaliburan.front.fragment.content_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pesonaliburan.R
import com.example.pesonaliburan.adapter.CityDestinationAdapter
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponseCityModel
import com.example.pesonaliburan.style.GridItemDecoration
import kotlinx.android.synthetic.main.fragment_destination.view.*
import kotlinx.android.synthetic.main.search_result_destination_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultDestinationFragment: Fragment() {

    lateinit var CityDestinationAdapter: CityDestinationAdapter

    companion object{
        fun getInstance(): SearchResultDestinationFragment =
            SearchResultDestinationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.search_result_destination_layout,container,false)

        //Recyler iew
        CityDestinationAdapter=CityDestinationAdapter(activity!!)
        view.rc_cityDestination.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        view.rc_cityDestination.addItemDecoration(GridItemDecoration(1,20,true))
        view.rc_cityDestination.adapter=CityDestinationAdapter

        val bundle = arguments
        if (bundle != null){
            var getBundleSearch = bundle.getString("dataSearch")
            search_city(getBundleSearch)
        }

        return view
    }

    private fun search_city(getBundleSearch: String?) {

        val apiService = Server.buildService(ApiService::class.java)
        val callRequest = getBundleSearch?.let { apiService.search_city(it) }
        callRequest?.enqueue(object: Callback<ResponseCityModel> {
            override fun onResponse(call: Call<ResponseCityModel>, response: Response<ResponseCityModel>) {
                var statusResponse = response.body()!!.status
                if (statusResponse == 200){
                    CityDestinationAdapter.setCityDestination(response.body()!!.data)
//                    pb_load.isVisible=false
                    Toast.makeText(context,"Pencarian berhasil ditemukan", Toast.LENGTH_SHORT).show()
                    pb_searchResult?.isVisible=false
                }
                else{
                    Toast.makeText(context,"Pencarian tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseCityModel>, t: Throwable) {
                Toast.makeText(context,"Maaf ada kesalahan dalam system kami", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

        })
    }
}