package com.example.ble_demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes

 class ListAdapter_BTLE_Devices(context: Context, @LayoutRes private val layoutResource: Int, private val objects : List<BTLE_Device>) : ArrayAdapter<BTLE_Device>(context , layoutResource, objects) {
     private var device = objects
     override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

         var convertV: View = LayoutInflater.from(context).inflate(layoutResource, parent, false) as View
        var btleDevice = device.get(position)
         var name = btleDevice.getName()
         var address = btleDevice.getAddress()
         var rssi = btleDevice.getRSSI()

         var tv_view  = convertV.findViewById(R.id.tv_name) as TextView
         if(name!=null && name.isNotEmpty()){
             tv_view.text = name
         }else{
             tv_view.text = "No Name"
         }

         var tv_rssi = convertV.findViewById(R.id.tv_rssi) as TextView
         tv_rssi.text = "RSSI: " + rssi.toString()

         var tv_address = convertV.findViewById(R.id.tv_macaddr) as TextView
         if(address!=null && address.isNotEmpty()){
             tv_address.text = address
         }else{
             tv_address.text = "No address"
         }
         return convertV


     }

 }
