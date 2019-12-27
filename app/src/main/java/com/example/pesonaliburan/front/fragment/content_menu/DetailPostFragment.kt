package com.example.pesonaliburan.front.fragment.content_menu


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.pesonaliburan.R
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponsePostModel
import com.example.pesonaliburan.style.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_post.*
import kotlinx.android.synthetic.main.fragment_detail_post.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class DetailPostFragment : Fragment() {

    companion object{
        fun getInstance():DetailPostFragment = DetailPostFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_post, container, false)

        val bundle = arguments
        val getPostId = bundle!!.getInt("post_id")

        val alertDialogBuilder = AlertDialog.Builder(activity)

        val positiveButtonClick = { dialog: DialogInterface, which: Int ->
            deletePost(getPostId)
        }

        val negativeButtonClick = { dialog: DialogInterface, which: Int ->
            Toast.makeText(activity, "TIDAK", Toast.LENGTH_SHORT).show()
        }

        view.iv_deletePost.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            with(builder)
            {
                setTitle("Pesona Liburan Alert")
                setMessage("Hapus Post?")
                setPositiveButton("Hapus", DialogInterface.OnClickListener(function = positiveButtonClick))
                setNegativeButton(android.R.string.no, negativeButtonClick)
                show()
            }
        }


        //Method
        get_detail_post(getPostId)

        return view
    }

    fun deletePost(postId: Int) {
        val apiService = Server.buildService(ApiService::class.java)
        val callRequest = apiService.deletePost(postId)
        callRequest.enqueue(object : Callback<ResponsePostModel> {

            override fun onResponse(call: Call<ResponsePostModel>, response: Response<ResponsePostModel>) {
                var statusResponse=response.body()?.status
                var dataResponse = response.body()?.data
                if (statusResponse == 200){
                    replace_fragment(ProfileFragment.getInstance())
                    Toast.makeText(context,"Hapus Post berhasil", Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(context,"Hapus Post gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponsePostModel>, t: Throwable) {
                Toast.makeText(context,"Belum terhubung internet", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

        })
    }

    private fun replace_fragment(fragment: Fragment) {
        val ft = (context as AppCompatActivity).supportFragmentManager
            .beginTransaction()
        ft.replace(R.id.fl_container,fragment)
        ft.addToBackStack("fragment")
        ft.commit()
    }

    private fun get_detail_post(postId: Int) {
        val apiService = Server.buildService(ApiService::class.java)
        val callRequest = apiService.get_detail_post(postId)
        callRequest.enqueue(object : Callback<ResponsePostModel> {

            override fun onResponse(call: Call<ResponsePostModel>, response: Response<ResponsePostModel>) {
                var statusResponse=response.body()?.status
                var dataResponse = response.body()?.data
                if (statusResponse == 200){
                    dataResponse?.forEach {
                        Picasso.with(context).load("http://192.168.1.12:8000/img/"+it.user_img).transform(CircleTransform()).into(iv_userImgDetail)
                        tv_userNameDetail.text = it.name_user
                        Picasso.with(context).load("http://192.168.1.12:8000/img/"+it.post_img).into(iv_postImgDetail)
                        tv_postTitleDetail.text=it.post_title
                        tv_postDateDetail.text=it.post_date
                        tv_postDetail.text=it.post
                    }

                }
                else{
                    Toast.makeText(context,response.body()!!.msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponsePostModel>, t: Throwable) {
                Toast.makeText(context,"Maaf system kami dalam masalah", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

        })
    }


}
