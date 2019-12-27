package com.example.pesonaliburan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pesonaliburan.R
import com.example.pesonaliburan.retrofit.model.ImgSubDestinationModel
import com.squareup.picasso.Picasso

class SubDestinationImgAdapter(val context: Context): RecyclerView.Adapter<SubDestinationImgAdapter.ViewHolder>() {

    var ListImgSubDestinationModel:List<ImgSubDestinationModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubDestinationImgAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rc_detail_sub_destination_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ListImgSubDestinationModel.size
    }

    override fun onBindViewHolder(holder: SubDestinationImgAdapter.ViewHolder, position: Int) {
        holder.tv_titleSubDestination.text = ListImgSubDestinationModel.get(position).title_subimg
        Picasso.with(context).load("https://apipesonaliburan.000webhostapp.com/img/"+ListImgSubDestinationModel.get(position).img_sb).into(holder.iv_subDestination)
    }

    fun setSubImgDestinationList(ListImgSubDestinationModel:List<ImgSubDestinationModel>){
        this.ListImgSubDestinationModel=ListImgSubDestinationModel
        notifyDataSetChanged()
    }

    class ViewHolder(val itemview: View):RecyclerView.ViewHolder(itemview){
        var iv_subDestination: ImageView = itemview.findViewById(R.id.iv_subDestination)
        var tv_titleSubDestination: TextView = itemview.findViewById(R.id.tv_titleSubDestination)
    }
}