package com.example.pesonaliburan.front.fragment.content_menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pesonaliburan.R
import com.example.pesonaliburan.adapter.ShowMoreDestinationAdapter
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponseDestinationModel
import com.example.pesonaliburan.style.GridItemDecoration
import kotlinx.android.synthetic.main.fragment_show_more_destination.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubResultDestinationFragment: Fragment() {
    lateinit var ShowMoreDestinationAdapter: ShowMoreDestinationAdapter
    companion object{
        fun getInstance():SubResultDestinationFragment=SubResultDestinationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.sub_result_destination_layout,container,false)
        //Recyler View
        ShowMoreDestinationAdapter = ShowMoreDestinationAdapter(activity!!)
        view.rc_showMoreDestination.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        view.rc_showMoreDestination.addItemDecoration(GridItemDecoration(1,15,true))
        view.rc_showMoreDestination.adapter=ShowMoreDestinationAdapter

        val bundle = arguments
        if (bundle != null){
            Log.d("TEST","BUNDLE SUKSESSS")
            var getCity_Id:Int = bundle.getInt("city_id")
            var getBundleSearch = bundle.getString("dataSearch")
            searchDestination(getCity_Id,getBundleSearch)
        }
        return view
    }

    private fun searchDestination(getCity_Id:Int,getBundleSearch: String?) {
        val apiService = Server.buildService(ApiService::class.java)
        val requestCall = getBundleSearch?.let { apiService.search_destination(getCity_Id, it) }
        requestCall?.enqueue(object :Callback<ResponseDestinationModel>{
            override fun onResponse(call: Call<ResponseDestinationModel>, response: Response<ResponseDestinationModel>) {
                var statusResponse = response.body()!!.status
                if (statusResponse == 200){
                    ShowMoreDestinationAdapter.setShowMoreDestinationList(response.body()!!.data)
//                    pb_load.isVisible=false
                    Toast.makeText(context,"Pencarian berhasil ditemukan", Toast.LENGTH_SHORT).show()
//                    pb_searchResult?.isVisible=false
                }
                else{
                    Toast.makeText(context,"Pencarian tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseDestinationModel>, t: Throwable) {
                Toast.makeText(context,"Maaf ada kesalahan dalam system kami", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

        })

    }


}