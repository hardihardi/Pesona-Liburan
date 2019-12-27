package com.example.pesonaliburan.front.fragment.content_menu


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager

import com.example.pesonaliburan.R
import com.example.pesonaliburan.adapter.ShowMoreDestinationAdapter
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponseDestinationModel
import kotlinx.android.synthetic.main.fragment_show_more_destination.*
import kotlinx.android.synthetic.main.fragment_show_more_destination.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class ShowMoreDestinationFragment : Fragment() {

    lateinit var ShowMoreDestinationAdapter:ShowMoreDestinationAdapter

    companion object{
        fun getInstance():ShowMoreDestinationFragment= ShowMoreDestinationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_show_more_destination, container, false)
        val bundle = arguments
        var getCity_Id:Int = bundle!!.getInt("city_id")
        view.tv_cityNameShowMore.text=bundle.getString("city_name")

        view.btn_searchDestination.setOnClickListener {
            var getSearchDestination=et_searchDestination.text.toString()
            val bundle2:Bundle = Bundle()
            bundle2.putInt("city_id",getCity_Id)
            bundle2.putString("dataSearch",getSearchDestination)
            val SubResultDestinationFragment:SubResultDestinationFragment = SubResultDestinationFragment()
            SubResultDestinationFragment.arguments = bundle2
            et_searchDestination.text.clear()
            replace_fragment(SubResultDestinationFragment)
        }

        //Recyler View
        ShowMoreDestinationAdapter = ShowMoreDestinationAdapter(activity!!)
        view.rc_showMoreDestination.layoutManager=GridLayoutManager(activity,2)
        view.rc_showMoreDestination.adapter=ShowMoreDestinationAdapter

        //Method
        get_show_more_destination(getCity_Id)
        return view
    }

    private fun replace_fragment(fragment: Fragment) {
        val ft = (context as AppCompatActivity).supportFragmentManager
            .beginTransaction()
        ft.replace(R.id.fl_container,fragment)
        ft.addToBackStack("fragment")
        ft.commit()
    }

    private fun get_show_more_destination(cityId: Int) {
        val apiService = Server.buildService(ApiService::class.java)
        val callRequest = apiService.get_show_more_destination(cityId)
        callRequest.enqueue(object :Callback<ResponseDestinationModel>{

            override fun onResponse(call: Call<ResponseDestinationModel>, response: Response<ResponseDestinationModel>) {
                var statusResponse=response.body()?.status
                if (statusResponse == 200){
                    ShowMoreDestinationAdapter.setShowMoreDestinationList(response.body()!!.data)
                    pb_showMoreDes.isVisible=false
                }
                else{
                    Toast.makeText(context,"data gagal diload",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseDestinationModel>, t: Throwable) {
                Toast.makeText(context,"Maaf system kami dalam masalah",Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

        })
    }


}
