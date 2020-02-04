package com.zanehowcott.ledblinds

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.control_layout.*
import java.io.IOException
import java.util.*

class ControlActivity: AppCompatActivity(){

    lateinit var LEDcolor : TextView

   


    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnectted: Boolean = false
        lateinit var m_address: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.control_layout)

        lateinit var Colorview : TextView
        m_address = intent.getStringExtra(SelectDeviceActivity.EXTRA_ADDRESS)

        ConnectToDevice(this).execute()

        var button1 = Button(this)
        button1 = findViewById(R.id.control_led_color) as Button
        button1.setOnClickListener {
            val intent1 = Intent(this, ColorSelectActivity::class.java);
            startActivity(intent1);
        }

        control_led_on.setOnClickListener { sendCommand("1") }
        control_led_off.setOnClickListener { sendCommand("0") }
        control_led_disconnect.setOnClickListener { disconnect() }

        val bundle = intent.extras
        val rLED = bundle.get("RLED")
        val gLED = bundle.get("GLED")
        val bLED = bundle.get("BLED")
       // Toast.makeText(applicationContext,RLED.toString()+" "+GLED.toString()+" "+BLED.toString(),Toast.LENGTH_LONG).show()
        LEDcolor = findViewById(R.id.led_color) as TextView

        val red : String = rLED.toString()
        val green : String = gLED.toString()
        val blue : String = bLED.toString()


        val str : String = "$red : $green : $blue"
        LEDcolor.text = str
    }

    private fun sendCommand(input: String){
        if (m_bluetoothSocket != null){
            try{
                m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            } catch (e: IOException){
                e.printStackTrace()
            }
        }

    }

    private fun disconnect(){
        if(m_bluetoothSocket != null){
            try{
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnectted = false
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
        finish()
    }

    private class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>(){
        private var connectSuccess: Boolean = true
        private val context: Context
        init {
            this.context = c

        }

        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Connecting....", "Please Wait")
        }

        override fun doInBackground(vararg params: Void?): String? {
            try{
                if(m_bluetoothSocket == null || !m_isConnectted){
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()
                }
            }catch (e: IOException){
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(!connectSuccess){
                Log.i("data", "Couldn't Connect")
            }else {
                m_isConnectted = true
            }
            m_progress.dismiss()
        }
    }
}