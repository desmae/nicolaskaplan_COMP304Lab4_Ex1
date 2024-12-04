package com.example.nicolaskaplan_comp304lab4_ex1

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

object MapCustomizer {
    fun addMarker(map: GoogleMap, location: LatLng, title: String) {
        map.addMarker(MarkerOptions().position(location).title(title))
    }
    fun drawPolyLine(map: GoogleMap, points: List<LatLng>) {
        val polylineOptions = PolylineOptions().addAll(points)
        map.addPolyline(polylineOptions)
    }
}