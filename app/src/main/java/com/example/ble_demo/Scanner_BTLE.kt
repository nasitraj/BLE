package com.example.ble_demo

import android.Manifest
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.os.ParcelUuid
import android.util.Log
import androidx.core.app.ActivityCompat
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Scanner_BTLE(mainActivity: MainActivity, scanPeriod: Long, signalStrength: Int) {

    private val ma = mainActivity
    private val sp = scanPeriod
    private val ss = signalStrength
    private var characteristics : ArrayList<String> = ArrayList()
    private var servicesmap : HashMap<String, ArrayList<String>> = HashMap()
    private var servicename : ArrayList<String> = ArrayList()
    private var bluetoothManager: BluetoothManager? = ma.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private var bluetoothAdapter: BluetoothAdapter = bluetoothManager!!.adapter
    private var bleScanCallBack :ScanCallback = object : ScanCallback(){
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            val device = result?.device
            if(device!=null){
                //val uuid  = device.uuids[0].uuid

               // Log.v("DeviceListActivity","onScanResult: ${device.address} - ${device.name}")
                //if(result!!.rssi > ss)
                    ma.addDevice(device,result!!.rssi,result)
                   // device.connectGatt(ma.applicationContext,false,gattCallBack)
                   // scanLeDevices(false)


                 // result?.let {  deviceFound(result.device) }

            }
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
            Log.d("DeviceListActivity","onBatchScanResults:${results.toString()}")
           results?.forEach { result ->
               ma.addDevice(result.device,result!!.rssi,result)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
           Log.d("DeviceListActivity", "onScanFailed: $errorCode")
        }
    }

    private var mScanning: Boolean = false

    private var utils = Utils()
    var hand = Handler(Looper.getMainLooper())

    fun isScanning() : Boolean {
        return mScanning
    }
    fun start(){
        if(!utils.checkBluetooth(bluetoothAdapter)){
            utils.requestBluetoohactivity(ma)
            ma.stopScan()
        }else{
            scanLeDevices(true)
        }
    }

    fun stop(){
         scanLeDevices(false)
    }

    private fun scanLeDevices(request : Boolean){
        if(request && !mScanning){
            utils.toast(ma.applicationContext, "Starting BLE Scan")
            hand.postDelayed({
                utils.toast(ma,"Stoping BLE Scan...")
                 mScanning = false
                if (ActivityCompat.checkSelfPermission(
                        ma,
                        Manifest.permission.BLUETOOTH_SCAN
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                bluetoothAdapter.bluetoothLeScanner.stopScan(bleScanCallBack)


                ma.stopScan()
            },sp)
            mScanning = true
            //val uuid = ParcelUuid("078CF035-0208-4F0F-8DD8-2DC1EEC41A71")
           /* val uuid = ParcelUuid.fromString("00001811-0000-1000-8000-00805f9b34fb")
            val filter = ScanFilter.Builder().setServiceUuid(uuid).build()
            val filters = listOf(filter)
            val setting = ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build()
            bluetoothAdapter.bluetoothLeScanner.startScan(filters,setting,bleScanCallBack)*/
            val uuid = ParcelUuid.fromString("00001803-0000-1000-8000-00805f9b34fb")
            val setting = ScanSettings.Builder().setScanMode(ScanSettings.CALLBACK_TYPE_ALL_MATCHES).build()
            val filter = ScanFilter.Builder().setServiceUuid(uuid).build()
            bluetoothAdapter.bluetoothLeScanner.startScan(null , setting, bleScanCallBack)
        }else{
            bluetoothAdapter.bluetoothLeScanner.stopScan(bleScanCallBack)
            utils.toast(ma.applicationContext, "Stopping BLE Scan")
        }

    }
    private fun BluetoothGatt.printGattTable() {
        if (services.isEmpty()) {
            Log.i("printGattTable", "No service and characteristic available, call discoverServices() first?")
            return
        }
        services.forEach { service ->
            val characteristicsTable = service.characteristics.joinToString(
                separator = "\n|--",
                prefix = "|--"
            ) { it.uuid.toString() }
            Log.i("printGattTable", "\nService ${service.uuid}\nCharacteristics:\n$characteristicsTable"
            )
        }
        services.forEach { service ->
            var char : ArrayList<String> = ArrayList()
            service.characteristics.forEach{characteristics0 ->
                char.add(characteristics0.uuid.toString())
            }
            if(servicesmap.containsKey(service.uuid.toString())){
                char.forEach {
                  if(!servicesmap.get(service.uuid.toString())!!.contains(it)){
                      servicesmap.get(service.uuid.toString())!!.add(it)
                    }
                }
            }else {
                servicesmap[service.uuid.toString()] = char
                if(!servicename.contains(service.uuid.toString())) {
                    servicename.add(service.uuid.toString())
                }
            }

            characteristics.clear()
        }
        val info = Services(servicename,servicesmap)
        if(services.isNotEmpty()){
            ma.setDeviceInfo(servicename,servicesmap)
        }
    }
}

