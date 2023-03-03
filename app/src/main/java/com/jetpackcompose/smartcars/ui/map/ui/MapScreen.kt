package com.jetpackcompose.smartcars.ui.map.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
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
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.jetpackcompose.smartcars.R



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
        LatLng(36.528311, -6.295017),
        LatLng(36.528935, -6.295966),
        LatLng(36.528921, -6.296589),
        LatLng(36.531182, -6.291643),
        LatLng(36.529223, -6.289575),
        LatLng(36.527809, -6.293338)
    )

    //Indicar en position la ubicación actual del usuario
    //Para que el mapa se abra en su ubicación
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(locations[0], 17f)
    }


    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions(MapStyle.json)
            )
        )
    }


    GoogleMap(modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true, mapType = MapType.HYBRID),
        googleMapOptionsFactory = { GoogleMapOptions().mapId(R.string.map_id.toString()) }
    ) {
        var loc = locations.shuffled()
        /*
        Marker(
            state = rememberMarkerState(position = loc[0]),
            title = "Tesla Model S",
            snippet = "Disponible",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
        )
        */
        MapMarker(
            position = loc[0],
            title = "Tesla Model S",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible"
        )
        MapMarker(
            position = loc[1],
            title = "Fiat 500e",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible"
        )
        MapMarker(
            position = loc[2],
            title = "MINI Cooper SE",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible"
        )
        MapMarker(
            position = loc[3],
            title = "Citroën AMI eléctrico",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible"
        )
        MapMarker(
            position = loc[4],
            title = "Cupra Born",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible"
        )
        MapMarker(
            position = loc[5],
            title = "Tesla model 3",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible"
        )
    }



}

fun bitmapDescriptor(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

//Marker custom para poder cambiar el icono
@Composable
fun MapMarker(
    context: Context,
    position: LatLng,
    title: String,
    snippet: String,
    @DrawableRes iconResourceId: Int
) {
    val icon = bitmapDescriptor(
        context, iconResourceId
    )
    Marker(
        state = MarkerState(position = position),
        title = title,
        snippet = snippet,
        icon = icon,
    )
}
