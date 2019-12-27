package com.example.pesonaliburan.front.fragment.content_menu


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.pesonaliburan.R
import com.example.pesonaliburan.adapter.CityDestinationAdapter
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponseCityModel
import com.example.pesonaliburan.retrofit.response.ResponseCountModel
import com.example.pesonaliburan.style.GridItemDecoration
import kotlinx.android.synthetic.main.fragment_destination.*
import kotlinx.android.synthetic.main.fragment_destination.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class DestinationFragment : Fragment() {

    lateinit var CityDestinationAdapter:CityDestinationAdapter

    companion object{
        fun getInstance(): DestinationFragment =
            DestinationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_destination,container,false)
        val getAreaId = activity!!.intent.extras!!.getInt("areaId")

        if (getAreaId == 1){
            view.tv_statusArea.text="Indonesia Bagian Barat"
        }
        else if(getAreaId == 2){
            view.tv_statusArea.text="Indonesia Bagian Tengah"
        }
        else if(getAreaId == 3){
            view.tv_statusArea.text="Indonesia Bagian Timur"
        }

        view.cv_destinationInfo.background = context?.let { ContextCompat.getDrawable(it,R.drawable.image2) }

        //Recyler iew
        CityDestinationAdapter=CityDestinationAdapter(activity!!)
        view.rc_cityDestination.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        view.rc_cityDestination.addItemDecoration(GridItemDecoration(1,20,true))
        view.rc_cityDestination.adapter=CityDestinationAdapter

        view.btn_searchCity.setOnClickListener {
            var getSearchDestination=et_searchDestination.text.toString()
            val bundle:Bundle = Bundle()
            bundle.putString("dataSearch",getSearchDestination)
            val SearchResultDestinationFragment:SearchResultDestinationFragment = SearchResultDestinationFragment()
            SearchResultDestinationFragment.arguments = bundle
            et_searchDestination.text.clear()
            replace_fragment(SearchResultDestinationFragment)
        }

        view.pb_listCity.isVisible=true
        view.pb_cityCount.isVisible=true
        view.pb_countDestination.isVisible=true


        //Method
        getCityDestination(getAreaId)
        getCountCity(getAreaId)
        getCountDestination(getAreaId)

        return view
    }

    private fun replace_fragment(fragment: Fragment) {
        val ft = (context as AppCompatActivity).supportFragmentManager
            .beginTransaction()
        ft.replace(R.id.fl_container,fragment)
        ft.addToBackStack("fragment")
        ft.commit()

    }


    private fun getCityDestination(areaId: Int) {
        val apiService = Server.buildService(ApiService::class.java)
        val callRequest = apiService.get_city(areaId)
        callRequest.enqueue(object:Callback<ResponseCityModel>{
            override fun onResponse(call: Call<ResponseCityModel>, response: Response<ResponseCityModel>) {
                var statusResponse = response.body()?.status
                if (statusResponse == 200){
                    CityDestinationAdapter.setCityDestination(response.body()!!.data)
                    pb_listCity?.isVisible=false
                }
                else{
                    Toast.makeText(activity,response.body()?.msg,Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseCityModel>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun getCountDestination(areaId: Int) {

        val apiService = Server.buildService(ApiService::class.java)
        val callRequest = apiService.get_count_destination(areaId)
        callRequest.enqueue(object:Callback<ResponseCountModel>{
            override fun onResponse(call: Call<ResponseCountModel>, response: Response<ResponseCountModel>) {
                var statusResponse = response.body()!!.status
                var dataResponse = response.body()!!.data
                if (statusResponse == 200){
                    dataResponse.forEach {
                        tv_countDestination?.text= it.count_destination.toString()
                    }
                    pb_countDestination?.isVisible=false
                    tv_countDestination?.setTextColor(Color.parseColor("#FFFFFF"))
                }
                else{
                    Toast.makeText(context,response.body()!!.msg,Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseCountModel>, t: Throwable) {
                Toast.makeText(context,"Maaf ada kesalahan dalam system kami",Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    private fun getCountCity(areaId: Int) {
        val apiService = Server.buildService(ApiService::class.java)
        val callRequest = apiService.get_count_city(areaId)
        callRequest.enqueue(object:Callback<ResponseCountModel>{
            override fun onResponse(call: Call<ResponseCountModel>, response: Response<ResponseCountModel>) {
                var statusResponse = response.body()!!.status
                var dataResponse = response.body()!!.data
                if (statusResponse == 200){
                    dataResponse.forEach {
                        tv_countCity?.text=it.count_city.toString()
                    }
                    pb_cityCount?.isVisible=false
                    tv_countCity?.setTextColor(Color.parseColor("#ffffff"))
                }
                else{
                    Toast.makeText(context,response.body()!!.msg,Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseCountModel>, t: Throwable) {
                Toast.makeText(context,"Maaf ada kesalahan dalam system kami",Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }







}
