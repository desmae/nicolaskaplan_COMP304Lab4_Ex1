package com.example.nicolaskaplan_comp304lab4_ex1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var locationFinder: LocationFinder
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationFinder = LocationFinder(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )
        }

        WorkManagerCode.setupWorkManager(this)
        LocationFinder(this).startLocationUpdates()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val location = LatLng(43.6532, -79.3832)
        map.addMarker(MarkerOptions().position(location).title("Toronto"))
        map.moveCamera(CameraUpdateFactory.newLatLng(location))

        // map interacting test

        val interactions = MapInteractions(map)
        interactions.setTapListener { latLng ->
            Log.d("MapInteractions", "Map tapped at: $latLng")
        }
        interactions.setLongPressListener { latLng ->
            Log.d("MapInteractions", "Map long-pressed at: $latLng")
        }

        // map customizer test (polyline test)

        val path = listOf(
            LatLng(43.6532, -79.3832),
            LatLng(40.7128, -74.0060) // toronto to new york (example)
        )
        MapCustomizer.drawPolyLine(map, path)



        locationFinder.startLocationUpdates()
        val locationText = findViewById<TextView>(R.id.location_text)
        locationFinder.getCurrentLocation { location ->
            location?.let {
                val message = "Lat: ${it.latitude}, Lng: ${it.longitude}"
                locationText.text = message

                val userLocation = LatLng(it.latitude, it.longitude)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                map.addMarker(MarkerOptions().position(userLocation).title("You are here"))
            } ?: run {
                locationText.text = "Location unavailable"
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        (supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment)?.onSaveInstanceState(outState)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // geofence stuff

                val geofenceManager = GeofenceManager(this)
                geofenceManager.addGeofence(
                    geofenceId = "TorontoGeofence",
                    latLng = LatLng(43.6532, -79.3832),
                    radius = 500f,
                    expirationDuration = Geofence.NEVER_EXPIRE
                ) { success ->
                    if (success) {
                        Log.d("MainActivity", "geofence added successfully")
                    } else {
                        Log.e("MainActivity", "failed to add geofence")
                    }
                }
            } else {
                Log.e("MainActivity", "Location not allowed")
            }
        }
    }
    override fun onResume() {
        super.onResume()
        (supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment)?.onResume()
    }

    override fun onPause() {
        super.onPause()
        (supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment)?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        (supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment)?.onDestroy()
    }
}

