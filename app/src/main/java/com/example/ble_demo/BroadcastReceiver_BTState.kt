package com.example.ble_demo

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BroadcastReceiver_BTState(activityContext : Context) : BroadcastReceiver(){

    var activitContex :Context;

    init {
        this.activitContex = activityContext
    }

    override fun onReceive(context : Context?, intent : Intent?) {
        val action : String? = intent?.action;

        if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
            val state : Int? =
                intent?.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR)
            when(state){
                BluetoothAdapter.STATE_OFF -> Toast.makeText(activitContex,"Bluetooth is off",Toast.LENGTH_SHORT)
                BluetoothAdapter.STATE_TURNING_OFF -> Toast.makeText(activitContex,"Bluetooth is turning off",Toast.LENGTH_SHORT)
                BluetoothAdapter.STATE_ON -> Toast.makeText(activitContex,"Bluetooth is on",Toast.LENGTH_SHORT)
                BluetoothAdapter.STATE_TURNING_ON -> Toast.makeText(activitContex,"Bluetooth is turning on",Toast.LENGTH_SHORT)
            }
        }
    }


}