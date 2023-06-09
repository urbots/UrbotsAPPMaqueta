package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

open class MainActivity : ComponentActivity() {
    private lateinit var sendButton: Button
    private lateinit var ipEditText: EditText
    private lateinit var missatgeText: EditText
    private lateinit var content: TextView
    private val PORT = 7002 // Puerto para enviar los datos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendButton = findViewById(R.id.send_button)
        ipEditText = findViewById(R.id.ip_edit_text)
        missatgeText = findViewById(R.id.missatge_text)
        content = findViewById(R.id.textView)
        sendButton.setOnClickListener {
            val ip = ipEditText.text.toString()
            val missatge = codificaMissatge(missatgeText.text.toString().toInt())
            try {
                sendMessage(ip, missatge)
                content.setText("Missatge enviat=>" + missatge)
            }catch (e: java.lang.Exception){
                content.setText("ERROR: "+e.toString())
            }
        }


    }
    private fun codificaMissatge(valor: Int): String {
        return "S0|10110000|"+valor+"|"+valor+"|"+valor+"*"
    }
    private fun sendMessage(ip: String, message: String) {
        Thread {
            try {
                val socket = DatagramSocket()
                val address = InetAddress.getByName(ip)

                val buffer = message.toByteArray() // Frame que se va a enviar
                val packet = DatagramPacket(buffer, buffer.size, address, PORT)
                socket.send(packet)

                socket.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}
