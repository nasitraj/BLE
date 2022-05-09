package com.example.ble_demo

import android.bluetooth.BluetoothDevice

class BTLE_Device(device: BluetoothDevice) {

    private var bluetoothDevice = device
    private var rssi: Int = 0

    fun getAddress(): String? {
        return bluetoothDevice.address
    }

    fun getName(): String? {
        return bluetoothDevice.name
    }

    fun setRSSI(rssi: Int) {
        this.rssi = rssi
    }

    fun getRSSI(): Int {
        return rssi
    }
}