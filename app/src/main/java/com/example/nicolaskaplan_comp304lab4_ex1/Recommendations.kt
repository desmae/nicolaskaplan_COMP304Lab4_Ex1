package com.example.nicolaskaplan_comp304lab4_ex1

import android.content.Context
import com.google.android.gms.maps.model.LatLng

class Recommendations(private val context: Context) {
    fun getRecommendations(currentLocation: LatLng): List<String> {
        // fake recommendations based on location
        return listOf(
            "check out the restaurant at ${currentLocation.latitude}, ${currentLocation.longitude}",
        )
    }

    fun showRecommendations(currentLocation: LatLng, onRecommendation: (List<String>) -> Unit) {
        val recommendations = getRecommendations(currentLocation)
        onRecommendation(recommendations)
    }
}