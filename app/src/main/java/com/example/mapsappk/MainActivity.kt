package com.example.mapsappk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() {

    private val places = arrayListOf(
        Place("FIAP Campus Vila Olimpia", LatLng(-23.5955843, -46.6851937),
            "Rua Olimpíadas, 186 - São Paulo - SP", 4.8f),
        Place("FIAP Campus Paulista", LatLng(-23.5643721, -46.652857),
        "Av. Paulista, 1106 - São Paulo - SP", 5.0f),
        Place("FIAP Campus Vila Mariana", LatLng(-23.5746685, -46.6232043),
        "Av. Lins de Vasconcelos, 1264 - São Paulo - SP", 4.8f)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync{
            googleMap -> addMarkers(googleMap)

            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()
                places.forEach{
                    bounds.include(it.latLng)
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(),100))
            }

        }
    }

    private fun addMarkers(googleMap: GoogleMap){
        places.forEach { place ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .snippet(place.address)
                    .position(place.latLng)
                    .icon(BitmapHelper.vectorToBitmap(this, R.drawable.ic_baseline_school_24,ContextCompat.getColor(this,R.color.purple_700)))
            )
        }
    }
}




data class Place(
    val name: String,
    val latLng: LatLng,
    val address: String,
    val rating: Float
)