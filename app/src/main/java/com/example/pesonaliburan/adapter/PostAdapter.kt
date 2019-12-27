package com.example.pesonaliburan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.pesonaliburan.R
import com.example.pesonaliburan.retrofit.model.PostModel
import com.example.pesonaliburan.style.CircleTransform
import com.squareup.picasso.Picasso

class PostAdapter(val context: Context): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    var ListPostModel:List<PostModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rc_post_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ListPostModel.size
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        Picasso.with(context).load("https://apipesonaliburan.000webhostapp.com/img/"+ListPostModel.get(position).user_img).transform(CircleTransform()).into(holder.iv_userImg)
        holder.tv_userName.text = ListPostModel.get(position).name_user
        Picasso.with(context).load("https://apipesonaliburan.000webhostapp.com/img/"+ListPostModel.get(position).post_img).resize(500,300).onlyScaleDown().into(holder.iv_postImg)
        holder.tv_postTitle.text = ListPostModel.get(position).post_title
        holder.tv_postDate.text = ListPostModel.get(position).post_date
        holder.tv_post.text=ListPostModel.get(position).post
    }

    fun setPostList(ListPostModel:List<PostModel>){
        this.ListPostModel = ListPostModel
        notifyDataSetChanged()
    }

    class ViewHolder(val itemview: View):RecyclerView.ViewHolder(itemview){
        var iv_userImg:ImageView = itemview.findViewById(R.id.iv_userImg)
        var tv_userName:TextView = itemview.findViewById(R.id.tv_userName)
        var iv_postImg:ImageView = itemview.findViewById(R.id.iv_postImg)
        var tv_postTitle:TextView = itemview.findViewById(R.id.tv_postTitle)
        var tv_postDate:TextView = itemview.findViewById(R.id.tv_postDate)
        var tv_post:TextView = itemview.findViewById(R.id.tv_post)

    }
}