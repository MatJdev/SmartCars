package com.jetpackcompose.smartcars.ui.map.ui

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jetpackcompose.smartcars.ui.home.ui.BottomNavigationBar
import com.jetpackcompose.smartcars.ui.home.ui.MyFab
import com.google.android.gms.location.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@Composable
fun MapScreen(navController: NavController) {
    Scaffold(navController)
}

//@Preview(showBackground = true, showSystemUi = true)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Scaffold(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavigationBar("map_screen", navController) },
        floatingActionButton = { MyFab() },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        Column {
            MyGoogleMaps()
        }
    }

}


@Composable
fun MyGoogleMaps() {
    val locations = listOf(
        LatLng(40.458519, -3.694451),
        LatLng(40.406642, -3.687911),
        LatLng(40.429759, -3.720115),
        LatLng(40.432436, -3.675649),
        LatLng(40.421797, -3.708347),
        LatLng(40.425873, -3.676015)
    )

    //Indicar en position la ubicación actual del usuario
    //Para que el mapa se abra en su ubicación
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(locations[0], 17f)
    }

    GoogleMap(modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true, mapType = MapType.HYBRID)
    ) {
        var loc = locations.shuffled()
        Marker(
            state = rememberMarkerState(position = loc[0]),
            title = "Tesla Model S",
            snippet = "Disponible",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
        )
        Marker(
            state = rememberMarkerState(position = loc[1]),
            title = "Tesla Model S",
            snippet = "Disponible",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
        )
        Marker(
            state = rememberMarkerState(position = loc[2]),
            title = "Tesla Model S",
            snippet = "Disponible",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )
        Marker(
            state = rememberMarkerState(position = loc[3]),
            title = "Tesla Model S",
            snippet = "Disponible",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        )
        Marker(
            state = rememberMarkerState(position = loc[4]),
            title = "Tesla Model S",
            snippet = "Disponible",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        )
        Marker(
            state = rememberMarkerState(position = loc[5]),
            title = "Tesla Model S",
            snippet = "Disponible",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
        )
    }


}
