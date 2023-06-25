package com.miempresa.googlemapv4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_destinos.*

class destinos : AppCompatActivity() {

    var destino = ""
    var latitud = ""
    var longitud = ""
    var info = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinos)

        val recibidos = this.intent.extras
        if (recibidos != null) {
            destino = intent.extras!!.getString("destino")!!
            latitud = intent.extras!!.getString("latitud")!!
            longitud = intent.extras!!.getString("longitud")!!
            info = intent.extras!!.getString("info")!!
        }
        lblDestino.setText(destino)
        lblCoordenadas.setText("$latitud , $longitud")
        lblInfo.setText(info)

        val imageResource = when (destino) {
            "plaza de armas" -> R.drawable.plaza_armas
            "CHARACATO" -> R.drawable.characato
            "COLCA" -> R.drawable.colca
            "YURA" -> R.drawable.yura
            "MIRADOR SACHACA" -> R.drawable.mirador_sachaca
            else -> R.drawable.error
        }

        val imgDestino = findViewById<ImageView>(R.id.imgDestino)
        imgDestino.setImageResource(imageResource)

        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            volverLista()
        }
    }

    private fun volverLista() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
