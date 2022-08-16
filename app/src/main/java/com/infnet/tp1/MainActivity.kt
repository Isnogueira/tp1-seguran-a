package com.infnet.tp1

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity(), LocationListener {

    //Código de permissão para localizaçao não precisa
    val COARSE_REQUEST = 12345
    //Código de permissão para localizaçao precisa
    val FINE_REQUEST = 67890

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGeolocalizacao = this.findViewById<Button>(R.id.btnGeolocalizacao)
        btnGeolocalizacao.setOnClickListener {
            //Tentar pegar obter a localização pela rede
            // Tentar obter a localização pelo GPS (Fallback)
            this.getLocationByNetwork()

        }
    }

    private fun getLocation(provider: String, permission: String) : Location? {
        // o retorno da localização
        var location: Location? = null
        // chave para acessar o Hardware através das bibliotecas nativas
        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isServiceEnabled = locationManager.isProviderEnabled(provider)
        if (isServiceEnabled) {
            Log.i("DR4", "Indo pela Rede")
            if (checkSelfPermission(permission) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    provider,
                    2000L,
                    0F,
                    this
                )
                location = locationManager.getLastKnownLocation(provider)
            } else {
                requestPermissions(arrayOf(permission), COARSE_REQUEST)
            }
        }
        return location
    }

    private fun getLocationByNetwork(){
        val location: Location? = this.getLocation(LocationManager.NETWORK_PROVIDER, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (location != null) {
            val lblLatitude = this.findViewById<TextView>(R.id.lblLatitude)
            lblLatitude.text = location.latitude.toString()
            val lblLongitude = this.findViewById<TextView>(R.id.lblLongitude)
            lblLongitude.text = location.longitude.toString()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == COARSE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }
    }

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }

}

