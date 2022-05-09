package com.example.ble_demo

import android.bluetooth.*
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener{

    private lateinit var mBTDevicehashMap : HashMap<String, BTLE_Device>
    private lateinit var mBTDeviceArrayList : ArrayList<BTLE_Device>
    private lateinit var adapter: ListAdapter_BTLE_Devices
    private lateinit var broadcastreceiverBtstate: BroadcastReceiver_BTState
    lateinit var utils : Utils
    private lateinit var mbtleScammer : Scanner_BTLE
    private var servicesmap1 : HashMap<String, ArrayList<String>> = HashMap()
    private var servicename1 : ArrayList<String> = ArrayList()
    private lateinit var info1 : Services

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        utils = Utils()
        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){

            utils.toast(applicationContext, "BLE not supported")
            finish()
        }

        broadcastreceiverBtstate = BroadcastReceiver_BTState(applicationContext)
        mbtleScammer = Scanner_BTLE(this,15000,-75)
        mBTDevicehashMap =  HashMap()
        mBTDeviceArrayList = ArrayList()

        adapter = ListAdapter_BTLE_Devices(this,R.layout.btle_device_list_item,mBTDeviceArrayList)

        var listView = ListView(this)
        listView.adapter = adapter
        listView.onItemClickListener = this
        findViewById<ScrollView>(R.id.scrollView).addView(listView)
        findViewById<Button>(R.id.btn_scan).setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastreceiverBtstate, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastreceiverBtstate)
        stopScan()
    }

    override fun onPause() {
        super.onPause()
        stopScan()
    }

    override fun onClick(v: View?) {

        if (v != null) {
            when(v.id){
                findViewById<Button>(R.id.btn_scan).id -> {
                    if (!mbtleScammer.isScanning()) startScan()
                    else stopScan()

                }
                }

            }
        }


    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val intent = Intent(this,Ble_Device_Info::class.java)
        intent.putExtra("names", servicename1)
        servicename1.forEach {
            intent.putExtra(it,servicesmap1.get(it))
        }

     /*   val args = Bundle()
        val args1 = Bundle()
        args.putSerializable("ServiceNames", servicename1 as Serializable)
        args1.putSerializable("Services", servicesmap1 as Serializable)
        intent.putExtra("ServiceName",args)
        intent.putExtra("Services",args1)*/
        startActivity(intent)
    }

    fun startScan(){
        findViewById<Button>(R.id.btn_scan).text = "Scanning..."

        mBTDeviceArrayList.clear()
        mBTDevicehashMap.clear()

        adapter.notifyDataSetChanged()

        mbtleScammer.start()
    }
    fun stopScan(){
        findViewById<Button>(R.id.btn_scan).text = "Scan Again"
        mbtleScammer.stop()
    }

    fun addDevice(device: BluetoothDevice, rssi : Int, result : ScanResult){
        val address = device.address
        val scanRecords = result.scanRecord

        if(scanRecords != null){
            val parcelUUID = scanRecords.serviceUuids
            if(parcelUUID != null){
                for( uuid in parcelUUID){
                    Log.v(
                        "Else block",
                        "onScanRecord: ${uuid.uuid}")
                }
            }
        }
        if(!mBTDevicehashMap.containsKey(address)){
            var btleDevice = BTLE_Device(device)
             btleDevice.setRSSI(rssi)
            mBTDevicehashMap[address] = btleDevice
            mBTDeviceArrayList.add(btleDevice)

            if(device.uuids != null) {
                Log.v(
                    "Else block",
                    "onScanResult: ${device.address} - ${device.name} - ${device.uuids[0].uuid}"
                )
                print("Hello")
            }else{
                Log.v(
                    "Else block",
                    "onScanResult: ${device.address} - ${device.name}")
            }
        }else{
            mBTDevicehashMap.get(address)!!.setRSSI(rssi)
        }
       adapter.notifyDataSetChanged()
    }
    fun setDeviceInfo(sername : ArrayList<String>, map : HashMap<String,ArrayList<String>>){
        servicename1 = sername
        servicesmap1 = map
    }

}
