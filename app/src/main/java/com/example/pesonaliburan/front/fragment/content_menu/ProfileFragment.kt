package com.example.pesonaliburan.front.fragment.content_menu


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager

import com.example.pesonaliburan.R
import com.example.pesonaliburan.adapter.PostProfileAdapter
import com.example.pesonaliburan.front.activity.ListSettingsActivity
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponsePostModel
import com.example.pesonaliburan.retrofit.response.ResponseUserModel
import com.example.pesonaliburan.sharedpreferences.SharedPreference
import com.example.pesonaliburan.style.CircleTransform
import com.example.pesonaliburan.style.GridItemDecoration
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    lateinit var postProfileAdapter:PostProfileAdapter

    companion object{
        fun getInstance():ProfileFragment=ProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_profile, container, false)

        val sharedPref = context?.let { SharedPreference(it) }
        val getAreaId = activity!!.intent.extras!!.getInt("areaId")
        val getUserId = sharedPref?.getValueInt("user_id")

        view.iv_listSsettings.setOnClickListener {
            var toListSettings = Intent(Intent(activity,ListSettingsActivity::class.java))
            toListSettings.putExtra("areaId",getAreaId)
            startActivity(toListSettings)
        }

        view.cardView3.background = context?.let { ContextCompat.getDrawable(it,R.drawable.image2) }
        //recyler View
        postProfileAdapter = PostProfileAdapter(activity!!)
        view.rc_postProfile.layoutManager=GridLayoutManager(activity,3)
        view.rc_postProfile.addItemDecoration(GridItemDecoration(3,3,true))
        view.rc_postProfile.adapter = postProfileAdapter

        //MEHOD
        get_profile(getUserId)
        get_postProfile(getUserId)
        return view


    }

    private fun get_postProfile(userId: Int?) {
        val apiService = Server.buildService(ApiService::class.java)
        val callRequest = apiService.get_post_user(userId!!)
        callRequest.enqueue(object :Callback<ResponsePostModel>{
            override fun onResponse(call: Call<ResponsePostModel>, response: Response<ResponsePostModel>) {
                var statusResponse=response.body()?.status
                if (statusResponse == 200){
                    response.body()?.data?.let { postProfileAdapter.setPostProfileList(it) }
//                    Toast.makeText(activity,response.body()?.msg, Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(activity,response.body()?.msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponsePostModel>, t: Throwable) {
                Toast.makeText(context,"Maaf ada kesalahan dalam system kami", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        }
        )
    }

    private fun get_profile(userId: Int?) {
        val apiService = Server.buildService(ApiService::class.java)
        val callRequest = apiService.get_profile(userId!!)
       callRequest.enqueue(object :Callback<ResponseUserModel>{
           override fun onResponse(call: Call<ResponseUserModel>, response: Response<ResponseUserModel>) {
               var statusResponse=response.body()?.status
               var dataResponse = response.body()?.data
               if (statusResponse == 200){
                   dataResponse?.forEach {
                       tv_nameUserProfile?.text = it.name_user
                       tv_nameUserProfile2?.text = it.name_user
                       tv_aboutUser?.text = it.about_user
                       Picasso.with(context)?.load("https://apipesonaliburan.000webhostapp.com/img/"+it.user_img)?.transform(CircleTransform())?.into(iv_userImg)
                   }
               }
               else{
                   Toast.makeText(context,response.body()!!.msg, Toast.LENGTH_SHORT).show()
               }
           }

           override fun onFailure(call: Call<ResponseUserModel>, t: Throwable) {
               Toast.makeText(context,"Maaf ada kesalahan dalam system kami", Toast.LENGTH_SHORT).show()
               t.printStackTrace()
           }
       }
       )
    }


}
