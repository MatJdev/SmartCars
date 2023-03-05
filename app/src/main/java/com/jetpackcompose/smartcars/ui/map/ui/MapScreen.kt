package com.jetpackcompose.smartcars.ui.map.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
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
import androidx.compose.material.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun MapScreen(navController: NavController) {
    //Scaffold(navController)
    BottomSheetScaffold(navController = navController)
}

//@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Scaffold(navController: NavController, bottomSheetState: BottomSheetScaffoldState) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavigationBar("map_screen", navController) },
        floatingActionButton = { MyFab() },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        Column {
            MyGoogleMaps(bottomSheetState)
        }
    }

}

//@Preview(showBackground = true, showSystemUi = true)
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetScaffold(navController: NavController) {
    //val scaffoldState = rememberBottomSheetScaffoldState()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            BottomSheet()
        },
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(
            topStart = 40.dp,
            topEnd = 40.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp)
    ) {
        // Screen content
        //MyGoogleMaps()
        Scaffold(navController, bottomSheetScaffoldState)

        /*
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

        GoogleMap(modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = true, mapType = MapType.HYBRID),
            googleMapOptionsFactory = { GoogleMapOptions().mapId(R.string.map_id.toString()) }
        ) {

            MarkerInfoWindow(
                state = MarkerState(position = LatLng(36.528470, -6.294143))
            ){ marker ->
                Toast.makeText(LocalContext.current, "Funciona", Toast.LENGTH_SHORT).show()
                val scope = rememberCoroutineScope()
                scope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed)
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    else
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                }

            }

        }
        */
    }
}


@Composable
fun BottomSheet() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)){
        Box(){
            Bg()
            Row(){
                TextButton(onClick = {
                },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFF2C2B34)
                    ), modifier = Modifier.padding(start = 330.dp, top = 8.dp)
                ) {
                    Icon(
                        Icons.Outlined.Cancel,
                        contentDescription = "",
                        Modifier
                            .size(18.dp),
                        tint = Color.White
                    )
                }
            }
            Column() {
                Row(modifier = Modifier.height(180.dp)){
                    Column(Modifier.padding(top = 30.dp, start = 10.dp)){
                        Text(text = "Tesla Model S", color = Color.White,
                            fontSize = 25.sp)
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(){
                            Icon(
                                Icons.Outlined.NearMe,
                                contentDescription = "",
                                Modifier
                                    .padding(start = 5.dp)
                                    .size(18.dp),
                                tint = Color.White
                            )
                            Text(text = "< 2km", modifier = Modifier.padding(start = 2.dp), color = Color.White)
                            Icon(
                                Icons.Outlined.BatteryChargingFull,
                                contentDescription = "",
                                Modifier
                                    .padding(start = 30.dp)
                                    .size(18.dp),
                                tint = Color.White
                            )
                            Text(text = "85%", modifier = Modifier.padding(start = 2.dp), color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(45.dp))
                        Text(text = "Características", color = Color.Black,
                            fontSize = 18.sp, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 10.dp))
                    }
                    Column(Modifier.padding(top = 25.dp, end = 10.dp)) {
                        AsyncImage(
                            model = "https://www.pngplay.com/wp-content/uploads/13/2018-Tesla-Model-S-Transparent-PNG.png",
                            contentDescription = null,
                            Modifier.fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(){
                    Card(
                        elevation = 5.dp,
                        border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
                        modifier = Modifier
                            .padding(start = 30.dp, end = 15.dp)
                            .width(100.dp)
                            .height(100.dp),
                        backgroundColor = Color(0xFFE6E6E6),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(Modifier.padding(start = 15.dp)){
                            Icon(
                                Icons.Outlined.BatteryChargingFull,
                                contentDescription = "",
                                Modifier
                                    .padding(start = 10.dp, top = 12.dp)
                                    .size(40.dp),
                                tint = Color(0xFF2C2B34)
                            )
                            Text(text = "Eléctrico", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Carga rápida", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                    Card(
                        elevation = 5.dp,
                        border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .width(100.dp)
                            .height(100.dp),
                        backgroundColor = Color(0xFFE6E6E6),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(Modifier.padding(start = 10.dp)){
                            Icon(
                                Icons.Outlined.Speed,
                                contentDescription = "",
                                Modifier
                                    .padding(start = 10.dp, top = 12.dp)
                                    .size(40.dp),
                                tint = Color(0xFF2C2B34)
                            )
                            Text(text = "Aceleración", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(text = "0 - 100 km/h: 2,1 s", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                    Card(
                        elevation = 5.dp,
                        border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
                        modifier = Modifier
                            .padding(end = 30.dp)
                            .width(100.dp)
                            .height(100.dp),
                        backgroundColor = Color(0xFFE6E6E6),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(Modifier.padding(start = 15.dp)){
                            Icon(
                                Icons.Outlined.Work,
                                contentDescription = "",
                                Modifier
                                    .padding(start = 10.dp, top = 12.dp)
                                    .size(40.dp),
                                tint = Color(0xFF2C2B34)
                            )
                            Text(text = "C.Maletero", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(text = "709 Litros", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(){
                    Text(text = "10€", Modifier.padding(start = 45.dp, top = 10.dp),
                        fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Text(text = "/hora", Modifier.padding(top = 23.dp),
                        fontSize = 15.sp)
                    Button(onClick = { /*TODO*/ },
                        Modifier
                            .padding(start = 90.dp, end = 30.dp)
                            .height(55.dp)
                            .width(130.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF2C2B34),
                            contentColor = Color.White
                        )) {
                        Text(text = "Alquilar", fontSize = 18.sp)
                    }
                }
            }

        }

    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyGoogleMaps(bottomSheetState: BottomSheetScaffoldState) {
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
        /*
        MarkerInfoWindow(
            state = MarkerState(position = LatLng(36.528470, -6.294143))
        ){ marker ->
            Toast.makeText(LocalContext.current, "Funciona", Toast.LENGTH_SHORT).show()
            val scope = rememberCoroutineScope()
            scope.launch {
                if (bottomSheetState.bottomSheetState.isCollapsed)
                    bottomSheetState.bottomSheetState.expand()
                else
                    bottomSheetState.bottomSheetState.collapse()
            }
        }
        */
        MapMarker(
            position = loc[0],
            title = "Tesla Model S",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState
        )
        MapMarker(
            position = loc[1],
            title = "Fiat 500e",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState
        )
        MapMarker(
            position = loc[2],
            title = "MINI Cooper SE",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState
        )
        MapMarker(
            position = loc[3],
            title = "Citroën AMI eléctrico",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState
        )
        MapMarker(
            position = loc[4],
            title = "Cupra Born",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState
        )
        MapMarker(
            position = loc[5],
            title = "Tesla model 3",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState
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
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapMarker(
    context: Context,
    position: LatLng,
    title: String,
    snippet: String,
    @DrawableRes iconResourceId: Int,
    bottomSheetState: BottomSheetScaffoldState
) {
    val icon = bitmapDescriptor(
        context, iconResourceId
    )
    MarkerInfoWindow(
        state = MarkerState(position = position),
        title = title,
        snippet = snippet,
        icon = icon
    ){ marker ->
        val scope = rememberCoroutineScope()
        scope.launch {
            if (bottomSheetState.bottomSheetState.isCollapsed)
                bottomSheetState.bottomSheetState.expand()
            else
                bottomSheetState.bottomSheetState.collapse()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Bg(){
    Box(){
        Box(modifier = Modifier
            .background(color = Color(0xFF2C2B34))
            .fillMaxSize()){
        }
        Box(
            modifier = Modifier
                .padding(top = 120.dp)
                .size(width = 400.dp, height = 800.dp)
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 40.dp,
                        topEnd = 40.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(color = Color(0xFFF3F3F3))
        ) {
        }
    }
}