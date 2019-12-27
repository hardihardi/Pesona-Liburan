package com.example.pesonaliburan.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pesonaliburan.R
import com.example.pesonaliburan.front.fragment.content_menu.DetailDestinationFragment
import com.example.pesonaliburan.front.fragment.content_menu.DetailSubDestinationFragment
import com.example.pesonaliburan.retrofit.model.DestinationModel

import com.squareup.picasso.Picasso

class ShowMoreDestinationAdapter(val context: Context): RecyclerView.Adapter<ShowMoreDestinationAdapter.ViewHolder>() {
    var ListDestinationModel:List<DestinationModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowMoreDestinationAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rc_showmoredestination_layout,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return ListDestinationModel.size
    }

    override fun onBindViewHolder(holder: ShowMoreDestinationAdapter.ViewHolder, position: Int) {
        holder.tv_nameShowMoreDestination.text = ListDestinationModel.get(position).destination_name
        Picasso.with(context).load("https://apipesonaliburan.000webhostapp.com/img/"+ListDestinationModel.get(position).destination_img).resize(150,250).onlyScaleDown().into(holder.iv_showMoreDestination)
        holder.cv_showMoreDestination.setOnClickListener {
            val bundle: Bundle = Bundle()
            bundle.putInt("destination_id", ListDestinationModel.get(position).destination_id!!)
            bundle.putString("destination_name",ListDestinationModel.get(position).destination_name)
            bundle.putString("destination_desc",ListDestinationModel.get(position).destination_desc)
            bundle.putString("destination_img",ListDestinationModel.get(position).destination_img)
            val DetailSubDestinationFragment:DetailSubDestinationFragment = DetailSubDestinationFragment()
            DetailSubDestinationFragment.arguments=bundle
            replace_fragment(DetailSubDestinationFragment)
        }
    }

    fun replace_fragment(fragment: Fragment){
        val tr = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        tr.replace(R.id.fl_container,fragment)
        tr.addToBackStack("fragment")
        tr.commit()

    }

    fun setShowMoreDestinationList(ListDestinationModel:List<DestinationModel>){
        this.ListDestinationModel=ListDestinationModel
        notifyDataSetChanged()

    }

    class ViewHolder(val itemview:View):RecyclerView.ViewHolder(itemview){
        var cv_showMoreDestination:CardView=itemview.findViewById(R.id.cv_showMoreDestination)
        var iv_showMoreDestination:ImageView=itemview.findViewById(R.id.iv_showMoreDestination)
        var tv_nameShowMoreDestination:TextView=itemview.findViewById(R.id.tv_nameShowMoreDestination)
    }
}