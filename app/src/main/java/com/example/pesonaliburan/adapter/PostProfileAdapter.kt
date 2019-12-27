package com.example.pesonaliburan.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pesonaliburan.R
import com.example.pesonaliburan.front.fragment.content_menu.DetailPostFragment
import com.example.pesonaliburan.retrofit.model.PostModel
import com.squareup.picasso.Picasso

class PostProfileAdapter(val context: Context): RecyclerView.Adapter<PostProfileAdapter.ViewHolder>() {

    var ListPostModel:List<PostModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostProfileAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rc_post_profile_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ListPostModel.size
    }

    fun setPostProfileList(ListPostModel:List<PostModel>){
        this.ListPostModel = ListPostModel
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PostProfileAdapter.ViewHolder, position: Int) {
        Picasso.with(context).load("https://apipesonaliburan.000webhostapp.com/img/"+ListPostModel.get(position).post_img).resize(300,300).onlyScaleDown().into(holder.iv_postProfile)
        holder.ct_postProfile.setOnClickListener {
            var bundle = Bundle()
            bundle.putInt("post_id", ListPostModel.get(position).post_id!!)
            var DetailPostFragment = DetailPostFragment()
            DetailPostFragment.arguments = bundle
            replace_fragment(DetailPostFragment)
        }
    }

    fun replace_fragment(fragment: Fragment){
        var tr = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        tr.replace(R.id.fl_container,fragment)
        tr.addToBackStack("fragment")
        tr.commit()
    }

    class ViewHolder(val itemview: View):RecyclerView.ViewHolder(itemview){
        var iv_postProfile: ImageView = itemview.findViewById(R.id.iv_postProfile)
        var ct_postProfile:CardView = itemview.findViewById(R.id.ct_postProfile)
    }
}