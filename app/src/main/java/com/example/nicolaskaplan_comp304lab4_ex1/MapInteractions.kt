package com.example.nicolaskaplan_comp304lab4_ex1

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

class MapInteractions(private val map: GoogleMap) {
    fun setTapListener(onTap: (LatLng) -> Unit) {
        map.setOnMapClickListener { latLng ->
            onTap(latLng)
        }
    }

    fun setLongPressListener(onLongPress: (LatLng) -> Unit) {
        map.setOnMapLongClickListener { latLng ->
            onLongPress(latLng)
        }
    }
}