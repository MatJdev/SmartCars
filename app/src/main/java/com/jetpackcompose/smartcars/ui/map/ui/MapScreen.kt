package com.jetpackcompose.smartcars.ui.map.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
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
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KProperty
import com.google.android.gms.maps.GoogleMap


@Composable
fun MapScreen(navController: NavController) {
    //Scaffold(navController)
    BottomSheetScaffold(navController = navController)
}

//@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Scaffold(navController: NavController, bottomSheetState: BottomSheetScaffoldState, modelo: MutableState<String>) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavigationBar("map_screen", navController) },
        floatingActionButton = { MyFab() },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        Column {
            MyGoogleMaps(bottomSheetState, modelo)
        }
    }

}

//@Preview(showBackground = true, showSystemUi = true)
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetScaffold(navController: NavController) {

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
    )
    //Habrá que crear variables como esta de modelo para poder cambiar los datos de detro del
    //BottomSheet desde otros componentes más concretamente desde el componente de los custom markers
    var modelo = rememberSaveable { mutableStateOf("")}

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            BottomSheet(modelo)
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
        Scaffold(navController, bottomSheetScaffoldState,modelo)

    }
}


@Composable
fun BottomSheet(modelo: MutableState<String>) {

    Log.i("TAG", modelo.value)
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
                        Text(text = modelo.value, color = Color.White,
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
fun MyGoogleMaps(bottomSheetState: BottomSheetScaffoldState, modelo: MutableState<String>) {
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
        position = CameraPosition.fromLatLngZoom(LatLng(36.529058, -6.294142), 17f)
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
        Polyline(
            points = listOf(
                LatLng(36.529058, -6.294142),
                LatLng(36.528935, -6.295966)
            ),
            color = Color.Green
        )
        */

        MapMarker(
            position = loc[0],
            title = "Tesla Model S",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState,
            modeloCar = modelo
        )
        MapMarker(
            position = loc[1],
            title = "Fiat 500e",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState,
            modeloCar = modelo
        )
        MapMarker(
            position = loc[2],
            title = "MINI Cooper SE",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState,
            modeloCar = modelo
        )
        MapMarker(
            position = loc[3],
            title = "Citroën AMI eléctrico",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState,
            modeloCar = modelo
        )
        MapMarker(
            position = loc[4],
            title = "Cupra Born",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState,
            modeloCar = modelo
        )
        MapMarker(
            position = loc[5],
            title = "Tesla model 3",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState,
            modeloCar = modelo
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

private fun <T> MutableState<T>.setValue(value: T) {
    this.value = value
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
    bottomSheetState: BottomSheetScaffoldState,
    modeloCar: MutableState<String>
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
            modeloCar.setValue(value = title)
            if (bottomSheetState.bottomSheetState.isCollapsed)
                bottomSheetState.bottomSheetState.expand()
            else
                bottomSheetState.bottomSheetState.collapse()
        }
        /*
        createRoute() //Descomentar cuando se termine de implementar el codigo para llamar a la api
        Log.i("aris", polyLineOptions.toString())
        */

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

//A partir de aquí es para la api de rutas
private var start: String = "-6.294142,36.529058"
private var end: String = "-6.295966,36.528935"


private fun createRoute() {
    CoroutineScope(Dispatchers.IO).launch {
        val call = getRetrofit().create(ApiRutasService::class.java)
            .getRoute("5b3ce3597851110001cf6248a49d27cc9f7449d29333c8dc442be77e", start, end)
        if (call.isSuccessful) {
            drawRoute(call.body())
        } else {
            Log.i("aris", "KO")
        }
    }
}

val polyLineOptions = mutableListOf<LatLng>()

private fun drawRoute(routeResponse: RouteResponse?) {
    //val polyLineOptions = PolylineOptions()

    routeResponse?.features?.first()?.geometry?.coordinates?.forEach {
        polyLineOptions.add(LatLng(it[1], it[0]))
    }
    /*
    runOnUiThread {
        //poly = map.addPolyline(polyLineOptions)
    }
    Polyline(
            points = polyLineOptions,
            color = Color.Green
        )
    */

}

private fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.openrouteservice.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}