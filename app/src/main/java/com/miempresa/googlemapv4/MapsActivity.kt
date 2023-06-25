package com.miempresa.googlemapv4

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import android.location.Geocoder
import android.location.Address
import android.net.Uri
import android.widget.Button
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.util.Locale

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    AdapterView.OnItemSelectedListener {

    private val Plaza = LatLng(-16.3988031, -71.5374435)
    private lateinit var mMap: GoogleMap

    private var destino = ""
    var marcadorDestino: Marker? = null
    var coordenada = LatLng(0.0, 0.0)

    private val tipos_mapas = intArrayOf(
        GoogleMap.MAP_TYPE_NONE,
        GoogleMap.MAP_TYPE_NORMAL,
        GoogleMap.MAP_TYPE_SATELLITE,
        GoogleMap.MAP_TYPE_HYBRID,
        GoogleMap.MAP_TYPE_TERRAIN
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        spnTipoMapa.setOnItemSelectedListener(this)

        val recibidos = this.intent.extras
        if (recibidos != null) {
            destino = intent.extras!!.getString("destino")!!
        }

        btnMoverCamara.setOnClickListener {
            moverCamara(it)
        }

        btnAddMarcador.setOnClickListener {
            agregarMarcador(it)
        }

        btnBuscar.setOnClickListener {
            val nombreLugar = etBuscar.text.toString()
            if (nombreLugar.isNotEmpty()) {
                buscarLugar(nombreLugar)
            } else {
                Toast.makeText(this, "Ingresa un lugar para buscar",
                    Toast.LENGTH_SHORT).show()
            }
        }

        val btnStreetView: Button = findViewById(R.id.btnStreetView)
        btnStreetView.setOnClickListener {
            val currentLatLng = mMap.cameraPosition.target
            val uri = Uri.parse("google.streetview:cbll=${currentLatLng.latitude}," +
                    "${currentLatLng.longitude}")
            val streetViewIntent = Intent(Intent.ACTION_VIEW, uri)
            streetViewIntent.setPackage("com.google.android.apps.maps")
            startActivity(streetViewIntent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.getUiSettings().setAllGesturesEnabled(true)
        mMap.getUiSettings().setZoomControlsEnabled(true)
        mMap.getUiSettings().setCompassEnabled(true)
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true)
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                123)
        }


        // Dentro del método onMapReady
        when (destino) {
            "plaza de armas" -> {
                coordenada = LatLng(-16.3988031, -71.5374435)
                marcadorDestino = mMap.addMarker(
                    MarkerOptions()
                        .position(coordenada)
                        .title("Conoce: $destino")
                )
            }
            "CHARACATO" -> {
                coordenada = LatLng(-16.468666, -71.484064)
                marcadorDestino = mMap.addMarker(
                    MarkerOptions()
                        .position(coordenada)
                        .title("Conoce: $destino")
                )
            }
            "COLCA" -> {
                coordenada = LatLng(-15.611461,-71.917446)
                marcadorDestino = mMap.addMarker(
                    MarkerOptions()
                        .position(coordenada)
                        .title("Conoce: $destino")
                )
            }
            "YURA" -> {
                coordenada = LatLng(-16.252798,-71.681364)
                marcadorDestino = mMap.addMarker(
                    MarkerOptions()
                        .position(coordenada)
                        .title("Conoce: $destino")
                )
            }
            "MIRADOR SACHACA" -> {
                coordenada = LatLng(-16.426295,-71.567449)
                marcadorDestino = mMap.addMarker(
                    MarkerOptions()
                        .position(coordenada)
                        .title("Conoce: $destino")
                )
            }
            else -> {
                Toast.makeText(this, "No se encontró el destino turístico",
                    Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        // Cámara
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenada, 15f))
        // Eventos
        mMap.setOnMarkerClickListener(this)
    }


    fun moverCamara(view: View?) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Plaza, 18f))
    }

    fun agregarMarcador(view: View?) {
        mMap.addMarker(
            MarkerOptions().position(
                LatLng(
                    mMap.cameraPosition.target.latitude,
                    mMap.cameraPosition.target.longitude
                )
            )
                .title("Mi ubicacion")
        )
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        if (p0 == marcadorDestino) {
            val intent = Intent(this, destinos::class.java)
            intent.putExtra("destino", destino)
            intent.putExtra("latitud", p0.position.latitude.toString())
            intent.putExtra("longitud", p0.position.longitude.toString())
            val mensaje = when (destino) {
                "plaza de armas" -> "La plaza Mayor o plaza de Armas de Arequipa, " +
                        "es uno de los principales espacios públicos de Arequipa " +
                        "y el lugar de fundación de la ciudad"

                "CHARACATO" -> "Characato es uno de los distritos que aún destaca " +
                        "por su verde campiña, siendo así uno de los pulmones " +
                        "de la ciudad de Arequipa"

                "COLCA" -> "El Cañón del Colca alcanza una profundidad de 4,160 metros, " +
                        "siendo así uno de los lugares más profundos del mundo." +
                        "Se formó por la erosión de roca volcánica provocada por el río " +
                        "Colca a lo largo de la línea de una falla en la corteza terrestre."

                "YURA" -> "Yura ocupa una extensa región de la provincia de Arequipa por lo " +
                        "que su territorio representa los accidentes más diversos: cadenas de " +
                        "cerros, llanuras, altiplanicies, quebradas y cañones, se halla en la " +
                        "vertiente oriental de los volcanes Misti y Chachani."

                "MIRADOR SACHACA" -> "Esta torre de 19 metros de altura fue construida en la cima " +
                        "del cerro que albergaba el antiguo cementerio de Sachaca, remodelado en 1996. " +
                        "El mirador tiene cinco pisos. Desde su terraza se puede apreciar a " +
                        "los volcanes Misti, Chachani y Pichu Pichu."

                else -> "No se encontró descripción"
            }
            intent.putExtra("info", mensaje)
            startActivity(intent)
            return true
        }
        return false
    }

    private fun buscarLugar(nombreLugar: String) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocationName(nombreLugar, 1)
                as List<Address>
        if (addresses.isNotEmpty()) {
            val address: Address = addresses[0]
            val latLng = LatLng(address.latitude, address.longitude)

            // Mueve la cámara al lugar buscado
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

            // Personaliza el icono del marcador
            val iconoMarcador = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)

            // Agrega el marcador en la ubicación buscada
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .icon(iconoMarcador)
            )
        }
    }
    private fun obtenerDireccion(latitud: Double, longitud: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val direcciones: List<Address> = geocoder.getFromLocation(latitud, longitud, 1)
                as List<Address>

        if (direcciones.isNotEmpty()) {
            val direccion = direcciones[0]
            val direccionCompleta = direccion.getAddressLine(0)
        }
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        mMap.setMapType(tipos_mapas[p2]);
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}
