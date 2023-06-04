package com.bliss.csc.smart_mask_sw9909.fragment


import android.Manifest
import android.Manifest.permission.BLUETOOTH
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bliss.csc.smart_mask_sw9909.databinding.SettingFragmentBinding
import com.bliss.csc.smart_mask_sw9909.fragment.recyclerview.MyAdapter
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*


class SettingFragment: Fragment() {

    var binding: SettingFragmentBinding? = null
    lateinit var device: BluetoothDevice
    var socket: BluetoothSocket? = null
    var outputStream: OutputStream? = null
    var inputStream: InputStream? = null

    private val TAG = "BluetoothExample"
    private val DEVICE_ADDRESS = "98:DA:60:05:04:8D" // 아두이노 블루투스 모듈의 MAC 주소
    private val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // 시리얼 통신을 위한 UUID




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = SettingFragmentBinding.inflate(inflater, container, false)


        binding!!.bluetoothOnButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_ADVERTISE,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ),
                    1
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.BLUETOOTH
                    ),
                    1
                )
            }
            binding!!.bluetoothOnButton.setBackgroundColor(Color.rgb(153,255,204))
            binding!!.bluetoothOffButton.setBackgroundColor(Color.WHITE)
        }




        //연결 가능한 bluetooth device listView
//        binding.spdCheckBox.setOnClickListener {
//            if (binding.spdCheckBox.isChecked){
//                binding.devicesRecyclerView.visibility = View.VISIBLE
//            }else{
//                binding.devicesRecyclerView.visibility = View.INVISIBLE
//            }
//        }

//        //bluetooth 연결 가능한 기기 recyclerView 출력
//        val datas = mutableListOf<String>()
//        for(i in 1..20){
//            datas.add("Item $i")
//        }


//        val adapter = MyAdapter(datas)
//        val layoutManager = LinearLayoutManager(activity)
//
//        binding.devicesRecyclerView.layoutManager = layoutManager
//        binding.devicesRecyclerView.adapter = adapter


        return binding!!.root
    }

    //bluetoothStart
    //TODO bluetooth 연결 코드 작성
    fun bluetoothStart(){
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter != null) {
            device = bluetoothAdapter.getRemoteDevice(DEVICE_ADDRESS)
            ConnectThread().start()
        }
    }

    fun bluetoothEnd(){
        binding!!.bluetoothOffButton.setOnClickListener {
            //TODO bluetooth 해제 코드 작성
            onDestroyView()
            binding!!.bluetoothOffButton.setBackgroundColor(Color.rgb(153,255,204))
            binding!!.bluetoothOnButton.setBackgroundColor(Color.WHITE)
            Log.d("off", "off is selected")
        }
    }


    //bluetooth Thread
    inner class ConnectThread : Thread() {
        @SuppressLint("MissingPermission")
        override fun run() {
            try {
                socket = device.createRfcommSocketToServiceRecord(MY_UUID)
                socket?.connect()
                Log.d(TAG, "Bluetooth connection established.")

                outputStream = socket?.outputStream
                inputStream = socket?.inputStream

                // 여기서부터 아두이노와의 통신 코드를 작성합니다.
                // 예를 들어, 아두이노로 데이터를 전송하려면 다음과 같이 사용합니다:
                val dataToSend = "Hello, Arduino!\n".toByteArray()
                outputStream?.write(dataToSend)

                // 아두이노로부터 데이터를 수신하려면 다음과 같이 사용합니다:
                val buffer = ByteArray(1024)
                val bytes = inputStream?.read(buffer)
                val receivedData = bytes?.let { String(buffer, 0, it) }
                if (receivedData != null) {
                    //데이터 받아오는 코드드
                }
               Log.d(TAG, "Received data: $receivedData")

            } catch (e: IOException) {
                Log.e(TAG, "Bluetooth connection failed.", e)
            }
        }
    }

    //bluetooth 해제
    override fun onDestroyView() {
        super.onDestroyView()
        try {
            outputStream?.close()
            inputStream?.close()
            socket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Failed to close the Bluetooth socket.", e)
        }
    }

}

