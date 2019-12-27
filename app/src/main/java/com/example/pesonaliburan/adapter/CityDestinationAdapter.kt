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
import androidx.recyclerview.widget.RecyclerView
import com.example.pesonaliburan.R
import com.example.pesonaliburan.front.fragment.content_menu.DetailCityFragment
import com.example.pesonaliburan.retrofit.model.CityModel
import com.squareup.picasso.Picasso



class CityDestinationAdapter(val context: Context): RecyclerView.Adapter<CityDestinationAdapter.ViewHolder>() {

    var listCityModel:List<CityModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityDestinationAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rc_citydestination_layout,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return listCityModel.size
    }

    override fun onBindViewHolder(holder: CityDestinationAdapter.ViewHolder, position: Int) {
        holder.tv_nameCity.text=listCityModel.get(position).city_name
        Picasso.with(context).load("https://apipesonaliburan.000webhostapp.com/img/"+listCityModel.get(position).city_img).resize(500,500).onlyScaleDown().into(holder.iv_cityDestination)
        holder.cv_cityDestination.setOnClickListener {
            val bundle:Bundle = Bundle()
            bundle.putInt("city_id", listCityModel.get(position).city_id!!)
            bundle.putString("city_name",listCityModel.get(position).city_name)
            bundle.putString("city_desc",listCityModel.get(position).city_desc)
            bundle.putString("city_img",listCityModel.get(position).city_img)
            val DetailCityFragment:DetailCityFragment = DetailCityFragment()
            DetailCityFragment.arguments = bundle
            replace_fragment(DetailCityFragment)
        }
    }

    fun replace_fragment(fragment: Fragment){
        val ft = (context as AppCompatActivity).supportFragmentManager
            .beginTransaction()
        ft.replace(R.id.fl_container,fragment)
        ft.addToBackStack("fragment")
        ft.commit()
    }


    fun setCityDestination(listCityModel:List<CityModel>){
        this.listCityModel=listCityModel
        notifyDataSetChanged()
    }

   class ViewHolder(val itemview:View):RecyclerView.ViewHolder(itemview){
       var tv_nameCity:TextView=itemview.findViewById(R.id.tv_nameShowMoreDestination)
       var iv_cityDestination:ImageView=itemview.findViewById(R.id.iv_showMoreDestination)
       var cv_cityDestination:CardView=itemview.findViewById(R.id.cv_showMoreDestination)



   }

}