package com.example.pesonaliburan.front.fragment.content_menu


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.pesonaliburan.R
import com.example.pesonaliburan.adapter.CityDestinationAdapter
import com.example.pesonaliburan.adapter.PostAdapter
import com.example.pesonaliburan.front.activity.AddPostActivity
import com.example.pesonaliburan.front.activity.LoginActivity
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponseDestinationModel
import com.example.pesonaliburan.retrofit.response.ResponsePostModel
import com.example.pesonaliburan.sharedpreferences.SharedPreference
import com.example.pesonaliburan.style.GridItemDecoration
import kotlinx.android.synthetic.main.fragment_destination.view.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import kotlinx.android.synthetic.main.rc_post_layout.*
import kotlinx.android.synthetic.main.rc_post_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.fragment_destination.*
import kotlinx.android.synthetic.main.fragment_post.*


/**
 * A simple [Fragment] subclass.
 */
class PostFragment : Fragment() {

    lateinit var PostAdapter:PostAdapter


    companion object{
        fun getInstance(): PostFragment = PostFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_post,container,false)
        val sharedPref: SharedPreference = SharedPreference(activity!!)
//        (activity as AppCompatActivity).setSupportActionBar(tb_post)
//        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
//        setHasOptionsMenu(true)
        //Recyler iew
        PostAdapter= PostAdapter(activity!!)
        view.rc_post.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
//        view.rc_post.addItemDecoration(GridItemDecoration(1,20,true))
        view.rc_post.adapter=PostAdapter
        val getAreaId = activity!!.intent.extras!!.getInt("areaId")

        view.iv_toUploadPost.setOnClickListener {
            if (sharedPref.getValueBoolean("status")){
                val toAddPostActivity = Intent(Intent(activity,AddPostActivity::class.java))
                startActivity(toAddPostActivity)
            }
            else{
                var intent = Intent(Intent(context, LoginActivity::class.java))
                intent.putExtra("areaId",getAreaId)
                activity!!.startActivity(intent)
                Toast.makeText(context,"Login terlebih dahulu", Toast.LENGTH_SHORT).show()
            }

        }
        //Method
        getPost();

        return view
    }

    private fun getPost() {
        val apiService = Server.buildService(ApiService::class.java)
        val callRequest = apiService.get_all_post()
        callRequest.enqueue(object : Callback<ResponsePostModel> {
            override fun onFailure(call: Call<ResponsePostModel>, t: Throwable) {
                Toast.makeText(context,"Maaf ada kesalahan dalam system kami", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponsePostModel>, response: Response<ResponsePostModel>) {
                var statusResponse=response.body()?.status
                if (statusResponse == 200){
                    PostAdapter.setPostList(response.body()!!.data)
                }
                else{
                    Toast.makeText(context,"gagal", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_post,menu)
        Log.d("SERCER","BERHASIL DIAMBIL")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.add_post -> {
                Log.d("SERVER","TESTTTT")
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
