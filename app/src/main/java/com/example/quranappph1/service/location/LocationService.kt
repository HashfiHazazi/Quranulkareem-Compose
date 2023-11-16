package com.example.quranappph1.service.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationService(
    private val client: FusedLocationProviderClient,
    private val context: Context
) {
    suspend fun getCurrentLocation(): LocationServiceCondition<Location?> {
        val isFineLocationPermitted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val isCoarseLocationPermitted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = context.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGpsEnabled) {
            return LocationServiceCondition.NoGps()
        }

        if (!(isFineLocationPermitted || isCoarseLocationPermitted)){
            return LocationServiceCondition.MissingPermission()
        }

        return suspendCancellableCoroutine { coroutine ->
            client.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).apply {
                if (isComplete){
                    if (isSuccessful){
                        coroutine.resume(LocationServiceCondition.Success(result)){}
                    }else{
                        coroutine.resume(LocationServiceCondition.Error()){}
                    }
                    return@suspendCancellableCoroutine
                }


                addOnSuccessListener {
                    coroutine.resume(LocationServiceCondition.Success(result)){}
                }

                addOnCanceledListener {
                    coroutine.cancel()
                }

                addOnFailureListener {
                    coroutine.resume(LocationServiceCondition.Error()){}
                }
            }
        }
    }
}