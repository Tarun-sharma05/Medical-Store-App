package com.pillport.app.ui_layer.Screens

import android.Manifest
import android.R.attr.apiKey
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontVariation.Settings
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import kotlin.coroutines.resumeWithException


@Composable
fun AmbulanceScreenUI(navController: NavController) {
GoogleMapScreen()

}

//@Composable
//fun GoogleMapScreen(){
//    // Define a location (e.g., San Francisco)
//    val sanFrancisco = LatLng(37.7749, -122.4194)
//
//    // Set the initial camera position
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(sanFrancisco, 10f)
//    }
//
//    // Display the Google Map
//    GoogleMap(
//        modifier = Modifier.fillMaxSize(),
//        cameraPositionState = cameraPositionState
//        )
//}

///////////////////////////////////////////////////////////////////////////
//


//////////////////////////////////////////////////////
//@Composable
//fun GoogleMapScreen() {
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//
//    // Get Google Maps API Key from Manifest
//    val apiKey = remember {
//        context.packageManager.getApplicationInfo(
//            context.packageName,
//            PackageManager.GET_META_DATA
//        ).metaData.getString("com.google.android.geo.API_KEY", "").also {
//            if (it.isEmpty()) Log.e("Maps", "Google Maps API key not found")
//        }
//    }
//
//    // User Location State
//    var userLocation by remember { mutableStateOf<LatLng?>(null) }
//    var hospitalLocations by remember { mutableStateOf<List<Place>>(emptyList()) }
//    var pharmacyLocations by remember { mutableStateOf<List<Place>>(emptyList()) }
//
//    // Google Map Camera Position
//    val cameraPositionState = rememberCameraPositionState()
//
//    // Fetch Location & Nearby Places on Launch
//    LaunchedEffect(Unit) {
//        coroutineScope.launch {
//            val location = getCurrentLocation(context)
//            userLocation = location
//            cameraPositionState.position = CameraPosition.fromLatLngZoom(location, 15f)
//
//            // Fetch Hospitals & Pharmacies
//            fetchNearbyPlaces(context, location, "hospital", apiKey) { hospitalLocations = it }
//            fetchNearbyPlaces(context, location, "pharmacy", apiKey) { pharmacyLocations = it }
//        }
//    }
//
//    // Google Map UI
//    Box(modifier = Modifier.fillMaxSize()) {
//        GoogleMap(
//            modifier = Modifier.fillMaxSize(),
//            cameraPositionState = cameraPositionState,
//            properties = MapProperties(isMyLocationEnabled = true),
//            uiSettings = MapUiSettings(zoomControlsEnabled = true)
//        ) {
//            // User Location Marker
//            userLocation?.let {
//                Marker(
//                    state = rememberUpdatedMarkerState(position = it),
//                    title = "Your Location",
//                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
//                )
//            }
//
//            // Hospital Markers (Red)
//            hospitalLocations.forEach { place ->
//                Marker(
//                    state = rememberUpdatedMarkerState(position = place.latLng),
//                    title = place.name ?: "Hospital",
//                    snippet = place.address,
//                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
//                )
//            }
//
//            // Pharmacy Markers (Green)
//            pharmacyLocations.forEach { place ->
//                Marker(
//                    state = rememberUpdatedMarkerState(position = place.latLng),
//                    title = place.name ?: "Pharmacy",
//                    snippet = place.address,
//                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
//                )
//            }
//        }
//    }
//}






@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GoogleMapScreen() {
    val context = LocalContext.current
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
     var showLocationDialog by remember { mutableStateOf(false) }

    var isLocationEnabled = remember { checkIfLocationEnabled(context) }

//    LaunchedEffect(Unit) {
//        if (!isLocationEnabled) {
//            showDialog = true
//        } else {
//            if (!locationPermissionState.status.isGranted) {
//                locationPermissionState.launchPermissionRequest()
//            }
//        }
//    }


    // Lifecycle observer to check when the user returns to the screen
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (locationPermissionState.status.isGranted) {
                    isLocationEnabled = checkIfLocationEnabled(context)
                    if (!isLocationEnabled) {
                        showLocationDialog = true
                    }
                } else {
                    locationPermissionState.launchPermissionRequest()
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }


            if (showLocationDialog) {
                AlertDialog(
                    onDismissRequest = { showLocationDialog = false },
                    title = { Text(text = "Location Services Disabled") },
                    text = { Text(text = "Please enable location services to use this feature.") },
                    confirmButton = {
                        Button(onClick = {
                            showLocationDialog = false
                            val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            context.startActivity(intent)
                        }) {
                            Text("Enable")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showLocationDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }


    if (locationPermissionState.status.isGranted) {
        ShowGoogleMap()
    } else {
        Text(text = "Location permission is required to use this feature.")
    }

    try {
        if (isLocationEnabled) {
            // Request location update
        } else {
            Toast.makeText(context, "Location is turned off", Toast.LENGTH_SHORT).show()
        }
    } catch (e: SecurityException) {
        e.printStackTrace()
        Toast.makeText(context, "Location permission error", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
    }


    }

// Helper function to check if location is enabled
fun checkIfLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}




//////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun ShowGoogleMap() {
    val context = LocalContext.current
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    val cameraPositionState = rememberCameraPositionState()
    val place = remember{mutableStateOf<List<Place>>(emptyList())}

    LaunchedEffect(Unit) {
        try {
            userLocation = getCurrentLocation(context)
        }catch (e: Exception){
            userLocation = LatLng(28.7041, 77.1025) // Default Location: Delhi, India
        }
        userLocation?.let {location ->
            cameraPositionState.position = CameraPosition.fromLatLngZoom(location, 15f)

            fetchNearbyPlaces( context = context,
                location = location,
                type = "pharmacy|hospital", onResult =   {result ->
                place.value = result
            },
                onError = {errorMessage ->
                    Log.e("PlacesError", errorMessage)
                }
            )
           }

    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true),
        uiSettings = MapUiSettings(zoomControlsEnabled = true)
    ) {
        userLocation?.let {
            Marker(
                state = rememberUpdatedMarkerState(position = it),
                title = "Your Location",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
        }
    }
}

///////////////////////////////////////////////////////////////////////////////
fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}


















//// Function to Get User Location
//suspend fun getCurrentLocation(context: Context): LatLng {
//    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//    return suspendCancellableCoroutine { cont ->
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            cont.resumeWithException(SecurityException("Location permission not granted"))
//            return@suspendCancellableCoroutine
//        }
//
//        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//            location?.let { cont.resume(LatLng(it.latitude, it.longitude)) }
//                ?: cont.resumeWithException(Exception("Location not available"))
//        }.addOnFailureListener { cont.resumeWithException(it) }
//    }
//}
////////////////////////////////////////////////////////////////////////////

// Function to Get User Location
suspend fun getCurrentLocation(context: Context): LatLng {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    return suspendCancellableCoroutine { cont ->
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            cont.resumeWith(Result.success(LatLng(28.7041, 77.1025))) // Default Location
            return@suspendCancellableCoroutine
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                cont.resumeWith(Result.success(LatLng(location.latitude, location.longitude)))
            } else {
                cont.resumeWith(Result.success(LatLng(28.7041, 77.1025))) // Default Location
            }
        }.addOnFailureListener {
            cont.resumeWith(Result.success(LatLng(28.7041, 77.1025))) // Default Location
        }

        cont.invokeOnCancellation { cause ->
            Log.e("Maps", "Location request cancelled: ${cause?.message}")
        }
    }
}







// Function to Fetch Nearby Places
//fun fetchNearbyPlaces(
//    context: Context,
//    location: LatLng,
//    type: String,
//    onResult: (List<Place>) -> Unit
//) {
//    val apiKey = getApiKey(context)
//    val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
//            "location=${location.latitude},${location.longitude}&" +
//            "radius=5000&type=$type&key=$apiKey"
//
//    CoroutineScope(Dispatchers.IO).launch {
//        try {
//            val response = URL(url).readText()
//            val jsonObject = JSONObject(response)
//            if (jsonObject.getString("status") != "OK") return@launch
//
//            val results = jsonObject.getJSONArray("results")
//            val places = mutableListOf<Place>()
//
//            for (i in 0 until results.length()) {
//                val place = results.getJSONObject(i)
//                val geometry = place.getJSONObject("geometry").getJSONObject("location")
//                places.add(
//                    Place(
//                        name = place.optString("name"),
//                        latLng = LatLng(geometry.getDouble("lat"), geometry.getDouble("lng")),
//                        address = place.optString("vicinity")
//                    )
//                )
//            }
//            withContext(Dispatchers.Main) { onResult(places) }
//        } catch (e: Exception) {
//            Log.e("Maps", "Error fetching places: ${e.message}")
//        }
//    }
//}
//


fun fetchNearbyPlaces(
    context: Context,
    location: LatLng,
    type: String,
    radius: Int = 5000,  // Allow radius customization
    onResult: (List<Place>) -> Unit,
    onError: (String) -> Unit  // Error callback
) {
    val apiKey = getApiKey(context)

    if (apiKey.isBlank()) {
        onError("API Key is missing or invalid")
        return
    }

    val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
            "location=${location.latitude},${location.longitude}&" +
            "radius=$radius&type=$type&key=$apiKey"

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = URL(url).readText()
            val jsonObject = JSONObject(response)

            if (jsonObject.getString("status") != "OK") {
                onError("Error: ${jsonObject.optString("error_message", "Unknown error")}")
                return@launch
            }

            val results = jsonObject.getJSONArray("results")
            val places = mutableListOf<Place>()

            for (i in 0 until results.length()) {
                val place = results.getJSONObject(i)
                val geometry = place.getJSONObject("geometry").getJSONObject("location")
                places.add(
                    Place(
                        name = place.optString("name"),
                        latLng = LatLng(geometry.getDouble("lat"), geometry.getDouble("lng")),
                        address = place.optString("vicinity")
                    )
                )
            }

            withContext(Dispatchers.Main) { onResult(places) }
        } catch (e: Exception) {
            Log.e("Maps", "Error fetching places: ${e.message}")
            withContext(Dispatchers.Main) { onError("Network error: ${e.message}") }
        }
    }
}






fun getApiKey(context: Context): String {
    return try {
        val appInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        appInfo.metaData?.getString("com.google.android.geo.API_KEY") ?: ""
    } catch (e: PackageManager.NameNotFoundException) {
        Log.e("Maps", "API Key not found in manifest")
        ""
    }
}




// Data Class for Places
data class Place(
    val name: String?,
    val latLng: LatLng,
    val address: String?
)