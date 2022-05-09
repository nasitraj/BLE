package com.example.ble_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_ble_device_info.*

class Ble_Device_Info : AppCompatActivity() {
    private var servicesmap1 : HashMap<String, ArrayList<String>> = HashMap()
    private var servicename1 : ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ble_device_info)
        servicename1 = intent?.getSerializableExtra("names") as ArrayList<String>
        servicename1.forEach{
            servicesmap1.put(it,intent.getSerializableExtra(it) as java.util.ArrayList<String>)
        }
        expandablelistview.setAdapter(Expandable_List_View_Adapter(this,servicesmap1, servicename1))
    }
}