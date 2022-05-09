package com.example.ble_demo

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.Gravity
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class Utils {



    fun checkBluetooth(bluetoothAdapter: BluetoothAdapter) : Boolean {
        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        return bluetoothAdapter.isEnabled()
    }

    fun requestBluetoohactivity(activity: Activity){
        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

       // activity.onRequestPermissionsResult(BluetoothAdapter.ACTION_REQUEST_ENABLE, arrayOf(permmission),)

        activity.startActivity(intent)
    }

    public fun toast(context: Context, string: String){
        var toast = Toast.makeText(context,string,Toast.LENGTH_SHORT)
        toast.show()
    }

     fun onRequestPermissionsResult(){

    }
    fun isPermissionGranted(activity: Activity , permission : String) : Boolean =
        ContextCompat.checkSelfPermission(
            activity.applicationContext,permission
        ) == PackageManager.PERMISSION_GRANTED
}