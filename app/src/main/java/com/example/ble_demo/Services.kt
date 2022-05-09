package com.example.ble_demo

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.os.Parcel
import android.os.Parcelable


data class Services (var gatt : ArrayList<String>,var services1 : HashMap<String,ArrayList<String>>) {
    private  var ser : ArrayList<String> = gatt
    private var services : HashMap<String,ArrayList<String>> = services1




    fun getServicesonly() : ArrayList<String> {
        return ser
    }
    fun getServices() : HashMap<String,ArrayList<String>>{
        return  services
    }




}

