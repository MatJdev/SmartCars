package com.jetpackcompose.smartcars.ui.map.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.*
import com.jetpackcompose.smartcars.R
import com.jetpackcompose.smartcars.ui.data.DataViewModel
import com.jetpackcompose.smartcars.ui.home.ui.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


@Composable
fun MapScreen(navController: NavController) {
    //Scaffold(navController)
    BottomSheetScaffold(navController = navController)
}

//@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Scaffold(navController: NavController,
             bottomSheetState: BottomSheetScaffoldState,
             modelo: MutableState<Int>,
             marcaCar: MutableState<String>,
             modeloCar: MutableState<String>,
             precio: MutableState<String>,
             imgCar: MutableState<String>,
             bateriaCar: MutableState<String>,
             motorCar: MutableState<String>,
             cargaCar: MutableState<String>,
             aceleracionCar: MutableState<String>,
             maleteroCar: MutableState<String>,
             distanciaCar: MutableState<Double>) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavigationBar("map_screen", navController) },
        floatingActionButton = { MyFab() },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        Column {
            MyGoogleMaps(bottomSheetState, modelo = modelo, precio = precio, imgCar = imgCar, marcaCar = marcaCar,
                modeloCar = modeloCar, bateriaCar = bateriaCar, motorCar = motorCar,
                aceleracionCar = aceleracionCar, maleteroCar = maleteroCar, cargaCar = cargaCar, distanciaCar = distanciaCar)
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
    var modelo = rememberSaveable { mutableStateOf(0)}
    var precio =  rememberSaveable { mutableStateOf("10") }
    var imgCar =  rememberSaveable { mutableStateOf("https://www.pngplay.com/wp-content/uploads/13/2018-Tesla-Model-S-Transparent-PNG.png") }
    var marcaCar =  rememberSaveable { mutableStateOf("Tesla") }
    var modeloCar =  rememberSaveable { mutableStateOf("Model S") }
    var bateriaCar =  rememberSaveable { mutableStateOf("85%") }
    var motorCar =  rememberSaveable { mutableStateOf("Eléctrico") }
    var aceleracionCar =  rememberSaveable { mutableStateOf("2,1 s") }
    var maleteroCar =  rememberSaveable { mutableStateOf("600 Litros") }
    var cargaCar =  rememberSaveable { mutableStateOf("Carga rápida") }
    var distanciaCar =  rememberSaveable { mutableStateOf(0.0) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            BottomSheet(distancia = distanciaCar, precio = precio, imgCar = imgCar, marcaCar = marcaCar,
            modeloCar = modeloCar, bateriaCar = bateriaCar, motorCar = motorCar,
            aceleracionCar = aceleracionCar, maleteroCar = maleteroCar, cargaCar = cargaCar)
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
        Scaffold(navController, bottomSheetScaffoldState, modelo = modelo, precio = precio, imgCar = imgCar, marcaCar = marcaCar,
            modeloCar = modeloCar, bateriaCar = bateriaCar, motorCar = motorCar,
            aceleracionCar = aceleracionCar, maleteroCar = maleteroCar, cargaCar = cargaCar, distanciaCar = distanciaCar)

    }
}


@Composable
fun BottomSheet(distancia: MutableState<Double>,
                dataViewModel: DataViewModel = viewModel(),
                precio: MutableState<String>,
                imgCar: MutableState<String>,
                marcaCar: MutableState<String>,
                modeloCar: MutableState<String>,
                bateriaCar: MutableState<String>,
                motorCar: MutableState<String>,
                cargaCar: MutableState<String>,
                aceleracionCar: MutableState<String>,
                maleteroCar: MutableState<String>) {
    val getData = dataViewModel.state.value

    Log.i("Datos en Map", getData.toString())
    /*Log.i("TAG", modelo.value)
    Log.i("Datos en Map", getData.toString())

    // Obtener una instancia del ViewModel

    // Llamar a la función para cargar los cursos desde Firestore
    LaunchedEffect(Unit) {
        viewModel.loadCourses()
    }
    // Observar los cambios en el StateFlow usando collectAsState
    val courses = viewModel.courses.collectAsState().value

    Log.i("Datos 3", courses.toString())*/

    /*var marca: String by remember { mutableStateOf("Tesla") }
    var modelo1: String by remember { mutableStateOf("Model S") }
    var bateria: String by remember { mutableStateOf("55%") }
    var imgCoche: String by remember { mutableStateOf("https://www.pngplay.com/wp-content/uploads/13/2018-Tesla-Model-S-Transparent-PNG.png") }
    var motor: String by remember { mutableStateOf("Gas") }
    var carga: String by remember { mutableStateOf("Carga rápida") }
    var aceleracion: String by remember { mutableStateOf("2,1 s") }
    var maletero: String by remember { mutableStateOf("709 Litros") }
    var precio: String by remember { mutableStateOf("10") }*/

    //Timer para que espere medio segundo a que el mapa cargue
    //Después con la corrutina va comprobando si ha recibido la info de Firestore
    //comprueba si el array esta vacío y si es así espera con el delay y vuelve a comprobarlo
    //cuando x fin recibe la info de firestore actualiza los datos
    /*Timer().schedule(500) {
        runBlocking {
            launch {
                while (getData == null || getData.isEmpty()) {
                    delay(200L) // esperar un segundo
                }
                // ejecutar código aquí
                Log.i("datos al final", getData.toString())
                marca = getData[modelo.value]!!.marca
                modelo1 = getData[modelo.value]!!.modelo
                bateria = getData[modelo.value]!!.bateria
                //imgCoche = getData[modelo.value]!!.img
                motor = getData[modelo.value]!!.motor
                carga = getData[modelo.value]!!.carga
                aceleracion = getData[modelo.value]!!.aceleracion
                maletero = getData[modelo.value]!!.maletero
            }
        }
    }*/

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
                        Text(text = marcaCar.value + " ${modeloCar.value}", color = Color.White,
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
                            Text(text = "< " + "%.2f".format(distancia.value) + " km", modifier = Modifier.padding(start = 2.dp), color = Color.White)
                            Icon(
                                Icons.Outlined.BatteryChargingFull,
                                contentDescription = "",
                                Modifier
                                    .padding(start = 30.dp)
                                    .size(18.dp),
                                tint = Color.White
                            )
                            Text(text = bateriaCar.value, modifier = Modifier.padding(start = 2.dp), color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(45.dp))
                        Text(text = "Características", color = Color.Black,
                            fontSize = 18.sp, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 10.dp))
                    }
                    Column(Modifier.padding(top = 25.dp, end = 10.dp)) {
                        AsyncImage(
                            model = imgCar.value,
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
                            Text(text = motorCar.value, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(text = cargaCar.value, fontSize = 10.sp, color = Color.Gray)
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
                            Text(text = "0 - 100 km/h: ${aceleracionCar.value}", fontSize = 10.sp, color = Color.Gray)
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
                            Text(text = maleteroCar.value, fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(){
                    Text(text = "${precio.value} €", Modifier.padding(start = 45.dp, top = 10.dp),
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
    /*Log.i("size", getData.size.toString())
    try {
        if (getData.size > 0) {
            bateria = getData[0].bateria
            imgCoche = getData[0].img
            motor = getData[0].motor
            carga = getData[0].carga
            aceleracion = getData[0].aceleracion
            maletero = getData[0].maletero
            precio = getData[0].precio
        } else {
            Log.i("Error size", "El size es de: " + getData.size.toString())
        }

    } catch (e: Error) {
        Log.d("error", "getDataFromFireStore: $e")
    }*/

    /*Timer().schedule(20000) {
        println("mundo")
        Log.i("datos al final", getData.toString())
        bateria = getData[0]!!.bateria
        imgCoche = getData[0]!!.img
        motor = getData[0]!!.motor
        carga = getData[0]!!.carga
        aceleracion = getData[0]!!.aceleracion
        maletero = getData[0]!!.maletero
    }*/


    /*bateria = getData[0]!!.bateria
    imgCoche = getData[0]!!.img
    motor = getData[0]!!.motor
    carga = getData[0]!!.carga
    aceleracion = getData[0]!!.aceleracion
    maletero = getData[0]!!.maletero*/
    //precio = getData.precio


    Log.i("datos al final", getData.toString())
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyGoogleMaps(bottomSheetState: BottomSheetScaffoldState,
                 modelo: MutableState<Int>,
                 precio: MutableState<String>,
                 imgCar: MutableState<String>,
                 marcaCar: MutableState<String>,
                 modeloCar: MutableState<String>,
                 bateriaCar: MutableState<String>,
                 motorCar: MutableState<String>,
                 cargaCar: MutableState<String>,
                 aceleracionCar: MutableState<String>,
                 maleteroCar: MutableState<String>,
                 distanciaCar: MutableState<Double>) {
    val locations = listOf(
        LatLng(36.528311, -6.295017),
        LatLng(36.528935, -6.295966),
        LatLng(36.528921, -6.296589),
        LatLng(36.531182, -6.291643),
        LatLng(36.529223, -6.289575),
        LatLng(36.527809, -6.293338)
    )

    //Ejemplo para calcular distancia entre dos puntos
    //Usar esto para calcular distancia entre ubicacion del usuario y los coches
    val distancia1 = SphericalUtil.computeDistanceBetween(locations[0], locations[1])
    Log.i("Distancia2", "%.2f".format(distancia1) + " metros")

    //Ubtener ubicación actual del usuario
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocalContext.current)

    obtenerUbicacion(LocalContext.current)

    Log.i("Ubicacion actual", lat.toString() + long.toString())

    var ubicacionActual = LatLng(lat, long)

    //Indicar en position la ubicación actual del usuario
    //Para que el mapa se abra en su ubicación
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(ubicacionActual, 17f)
    }


    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions(MapStyle.json)
            )
        )
    }

    /*var latCar =  rememberSaveable { mutableStateOf(0.0) }
    var lngCar =  rememberSaveable { mutableStateOf(0.0) }*/

    GoogleMap(modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true, mapType = MapType.HYBRID),
        googleMapOptionsFactory = { GoogleMapOptions().mapId(R.string.map_id.toString()) }
    ) {
        //var loc = locations.shuffled()

        /*
        Polyline(
            points = listOf(
                LatLng(36.529058, -6.294142),
                LatLng(36.528935, -6.295966)
            ),
            color = Color.Green
        )
        */
        //Hay que cambiar la posicion de cada marker con la lat y lng de cada objeto coche
        MapMarker(
            position = locations[2],
            title = "Tesla Model S",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState,
            modeloNum = modelo,
            precioCar = precio,
            num = 0,
            imgCar = imgCar,
            marcaCar = marcaCar,
            modeloCar = modeloCar,
            bateriaCar = bateriaCar,
            motorCar = motorCar,
            cargaCar = cargaCar,
            aceleracionCar = aceleracionCar,
            maleteroCar = maleteroCar,
            distanciaCar = distanciaCar,
            ubicacionActual = ubicacionActual
        )
        MapMarker(
            position = locations[4],
            title = "Fiat 500e",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState,
            modeloNum = modelo,
            precioCar = precio,
            num = 1,
            imgCar = imgCar,
            marcaCar = marcaCar,
            modeloCar = modeloCar,
            bateriaCar = bateriaCar,
            motorCar = motorCar,
            cargaCar = cargaCar,
            aceleracionCar = aceleracionCar,
            maleteroCar = maleteroCar,
            distanciaCar = distanciaCar,
            ubicacionActual = ubicacionActual
        )
        MapMarker(
            position = locations[1],
            title = "MINI Cooper SE",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState,
            modeloNum = modelo,
            precioCar = precio,
            num = 2,
            imgCar = imgCar,
            marcaCar = marcaCar,
            modeloCar = modeloCar,
            bateriaCar = bateriaCar,
            motorCar = motorCar,
            cargaCar = cargaCar,
            aceleracionCar = aceleracionCar,
            maleteroCar = maleteroCar,
            distanciaCar = distanciaCar,
            ubicacionActual = ubicacionActual
        )
        MapMarker(
            position = locations[5],
            title = "Citroën AMI E",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState,
            modeloNum = modelo,
            precioCar = precio,
            num = 3,
            imgCar = imgCar,
            marcaCar = marcaCar,
            modeloCar = modeloCar,
            bateriaCar = bateriaCar,
            motorCar = motorCar,
            cargaCar = cargaCar,
            aceleracionCar = aceleracionCar,
            maleteroCar = maleteroCar,
            distanciaCar = distanciaCar,
            ubicacionActual = ubicacionActual
        )
        MapMarker(
            position = locations[0],
            title = "Cupra Born",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState,
            modeloNum = modelo,
            precioCar = precio,
            num = 4,
            imgCar = imgCar,
            marcaCar = marcaCar,
            modeloCar = modeloCar,
            bateriaCar = bateriaCar,
            motorCar = motorCar,
            cargaCar = cargaCar,
            aceleracionCar = aceleracionCar,
            maleteroCar = maleteroCar,
            distanciaCar = distanciaCar,
            ubicacionActual = ubicacionActual
        )
        MapMarker(
            position = locations[3],
            title = "Tesla model 3",
            context = LocalContext.current,
            iconResourceId = R.drawable.marker5,
            snippet = "Disponible",
            bottomSheetState = bottomSheetState,
            modeloNum = modelo,
            precioCar = precio,
            num = 5,
            imgCar = imgCar,
            marcaCar = marcaCar,
            modeloCar = modeloCar,
            bateriaCar = bateriaCar,
            motorCar = motorCar,
            cargaCar = cargaCar,
            aceleracionCar = aceleracionCar,
            maleteroCar = maleteroCar,
            distanciaCar = distanciaCar,
            ubicacionActual = ubicacionActual
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

fun <T> MutableState<T>.setValue(value: T) {
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
    modeloNum: MutableState<Int>,
    precioCar: MutableState<String>,
    dataViewModel: DataViewModel = viewModel(),
    num: Int,
    imgCar: MutableState<String>,
    marcaCar: MutableState<String>,
    modeloCar: MutableState<String>,
    bateriaCar: MutableState<String>,
    motorCar: MutableState<String>,
    cargaCar: MutableState<String>,
    aceleracionCar: MutableState<String>,
    maleteroCar: MutableState<String>,
    distanciaCar: MutableState<Double>,
    ubicacionActual: LatLng
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
            modeloNum.setValue(value = num)
            val getData = dataViewModel.state.value
            //getData[0]?.let { precioCar.setValue(value = it.precio) }
            /*runBlocking {
                launch {
                    while (getData == null || getData.isEmpty()) {
                        delay(200L) // esperar un segundo
                    }
                    // ejecutar código aquí
                    Log.i("datos al final", getData.toString())
                    precioCar.setValue(getData[num]!!.precio)
                    imgCar.setValue(getData[num]!!.img)
                    modeloCar.setValue(getData[num]!!.modelo)
                    marcaCar.setValue(getData[num]!!.marca)
                    bateriaCar.setValue(getData[num]!!.bateria)
                    motorCar.setValue(getData[num]!!.motor)
                    cargaCar.setValue(getData[num]!!.carga)
                    aceleracionCar.setValue(getData[num]!!.aceleracion)
                    maleteroCar.setValue(getData[num]!!.maletero)
                }
            }*/
            while (getData == null || getData.isEmpty()) {
                delay(200L) // esperar un segundo
            }
            // ejecutar código aquí
            Log.i("datos al final", getData.toString())
            precioCar.setValue(getData[num]!!.precio)
            imgCar.setValue(getData[num]!!.img)
            modeloCar.setValue(getData[num]!!.modelo)
            marcaCar.setValue(getData[num]!!.marca)
            bateriaCar.setValue(getData[num]!!.bateria)
            motorCar.setValue(getData[num]!!.motor)
            cargaCar.setValue(getData[num]!!.carga)
            aceleracionCar.setValue(getData[num]!!.aceleracion)
            maleteroCar.setValue(getData[num]!!.maletero)
            val distancia1 = SphericalUtil.computeDistanceBetween(ubicacionActual, LatLng(getData[num]!!.latitud, getData[num]!!.longitud))
            distanciaCar.setValue(distancia1/1000)
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

