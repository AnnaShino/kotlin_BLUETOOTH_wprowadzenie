package com.example.bluetooth_k

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    //REQUEST_CODE_ENABLE_BT
    private val REQUEST_CODE_ENABLE_BT: Int = 1;

    //REQUEST_CODE_DISCOVERABLE_BT
    private val REQUEST_CODE_DISCOVERABLE_BT: Int = 2;

    //BTadapter
    lateinit var BTadapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFeatureInt(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        this.supportActionBar!!.hide()
        setContentView(R.layout.activity_main)

        //init BTadapter
        BTadapter = BluetoothAdapter.getDefaultAdapter()
        findViewById<TextView>(R.id.statusBT).text = "Bluetooth jest"

        //ikona zgodna ze statusem
        if (BTadapter.isEnabled) {
            findViewById<ImageView>(R.id.imageviewBT).setImageResource(R.drawable.ic_bluetoothon)
        } else {
            findViewById<ImageView>(R.id.imageviewBT).setImageResource(R.drawable.ic_bluetoothoff)
        }

        //włączenie BT
        findViewById<Button>(R.id.turnOnBT).setOnClickListener {
            if (BTadapter.isEnabled) {
                Toast.makeText(this, "Obecnie włączony", Toast.LENGTH_LONG).show()
            } else {
                //włączenie BT
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_CODE_ENABLE_BT);
            }
        }
        //wyłączenie BT
        findViewById<Button>(R.id.turnOffBT).setOnClickListener {
            if (!BTadapter.isEnabled) {
                Toast.makeText(this, "Obecnie wyłączony", Toast.LENGTH_LONG).show()
            } else {
                //wyłączenie BT
                BTadapter.disable()
                findViewById<ImageView>(R.id.imageviewBT).setImageResource(R.drawable.ic_bluetoothoff)
                Toast.makeText(this, "Bluetooth wyłączony", Toast.LENGTH_LONG).show()
            }
        }
        //wykrywalność BT
        findViewById<Button>(R.id.wykrywalnoscBT).setOnClickListener {
            if (!BTadapter.isDiscovering) {
                Toast.makeText(this, "Urządzenie widoczne", Toast.LENGTH_LONG).show()
                val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent, REQUEST_CODE_DISCOVERABLE_BT)
            }
        }
        //lista urządzeń parowalnych
        findViewById<Button>(R.id.parowanieBT).setOnClickListener {
            if (BTadapter.isEnabled) {
                //lista urządzeń
                val urzadzenia = BTadapter.bondedDevices
                var lista = ""
                for (urzadzenie in urzadzenia) {
                    val nazwaU = urzadzenie.name
                    val adresU = urzadzenie
                    lista += ("Urządzenie: $nazwaU , $adresU , \n")
                }
                findViewById<TextView>(R.id.textviewBT).text = ("Urządzenia parowalne \n" + "$lista")
            }
            else {
                Toast.makeText(this, "Najpierw włącz Bluetooth", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_ENABLE_BT ->
                if (resultCode == RESULT_OK) {
                    findViewById<ImageView>(R.id.imageviewBT).setImageResource(R.drawable.ic_bluetoothon)
                    Toast.makeText(this, "Bluetooth włączony", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Nie można włączyć Bluetooth", Toast.LENGTH_LONG).show()
                }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
