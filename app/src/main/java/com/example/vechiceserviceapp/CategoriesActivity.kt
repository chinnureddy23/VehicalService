package com.example.vechiceserviceapp


import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale


class CategoriesActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mGoogleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var addressTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupLocationCallback()

        addressTextView = this.findViewById(R.id.addressTextView)
    }

    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { updateLocation(it) }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        enableMyLocation()
    }

    private fun enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mGoogleMap?.isMyLocationEnabled = true
            startLocationUpdates()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 2000
            }

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        } else {

            Log.e("LocationError", "Location permission not granted")
        }
    }

    private val handler = Handler()
    private val delayMillis: Long = 10 * 60 * 1000


    private fun updateLocation(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        mGoogleMap?.clear()
        mGoogleMap?.addMarker(MarkerOptions().position(latLng).title("Current Location"))
        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))


        displayAddress(location)

        handler.postDelayed({
            moveToNextScreen(location, getAddress(location))
        }, delayMillis)
    }

    private fun displayAddress(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>? =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)

        addresses?.let {
            if (it.isNotEmpty()) {
                val address = it[0]
                val addressText = "${address.getAddressLine(0)}"
                addressTextView.text = addressText
                Log.d("LocationAddress", addressText)
                Log.d(
                    "LocationDetails",
                    "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
                )
                Log.d(
                    "LocationDetails",
                    "Country: ${address.countryName}, Locality: ${address.locality}, Admin Area: ${address.adminArea}"
                )
                moveToNextScreen(location, addressText)
            }
        }
    }

    private var isHomeActivityStarted = false

    private fun moveToNextScreen(location: Location, addressText: String) {
        if (!isHomeActivityStarted) {
            isHomeActivityStarted = true

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("latitude", location.latitude)
            intent.putExtra("longitude", location.longitude)
            intent.putExtra("address", addressText)
            startActivity(intent)
            finish()
        }
    }


    private fun getAddress(location: Location): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>? =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)

        return addresses?.firstOrNull()?.getAddressLine(0) ?: ""
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            enableMyLocation()
        } else {

            Log.e("LocationError", "Location permission not granted")
        }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}