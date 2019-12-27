package com.example.pesonaliburan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pesonaliburan.R
import com.example.pesonaliburan.retrofit.model.DestinationModel
import com.squareup.picasso.Picasso

class SubDestinationAdapter(val context: Context): RecyclerView.Adapter<SubDestinationAdapter.ViewHolder>() {

    var ListDestinationModel:List<DestinationModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubDestinationAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rc_subdestination_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ListDestinationModel.size
    }

    override fun onBindViewHolder(holder: SubDestinationAdapter.ViewHolder, position: Int) {
        holder.tv_nameSubDestination.text = ListDestinationModel.get(position).destination_name
        Picasso.with(context).load("https://apipesonaliburan.000webhostapp.com/img/"+ListDestinationModel.get(position).destination_img).resize(150,200).onlyScaleDown().into(holder.iv_subDestination)
    }

    fun setSubDestinationList(ListDestinationModel:List<DestinationModel>){
        this.ListDestinationModel=ListDestinationModel
        notifyDataSetChanged()
    }

    class ViewHolder(val itemview:View):RecyclerView.ViewHolder(itemview){
        var iv_subDestination:ImageView = itemview.findViewById(R.id.iv_showMoreDestination)
        var tv_nameSubDestination:TextView = itemview.findViewById(R.id.tv_nameShowMoreDestination)
    }
}