package com.jetpackcompose.smartcars.ui.home.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.rememberCameraPositionState
import com.jetpackcompose.smartcars.R
import com.jetpackcompose.smartcars.navigation.AppScreens
import com.google.maps.android.compose.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.maps.android.SphericalUtil
import com.jetpackcompose.smartcars.model.Web3jSingleton
import com.jetpackcompose.smartcars.ui.data.DataViewModel
import com.jetpackcompose.smartcars.ui.data.UserViewModel
import com.jetpackcompose.smartcars.ui.data.model.MyArgs
import com.jetpackcompose.smartcars.ui.map.ui.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigInteger


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(navController: NavController) {

    //Solicita permisos al usuario de Ubicación
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(key1 = true) {
        permissionState.launchPermissionRequest()
    }

    if (permissionState.status.isGranted) {
        Log.i("Permisos", "Permisos aceptados")
    } else {
        Log.i("Permisos", "Permisos denegados")
    }

    readUserData("0")
    Log.i("FIre Data User", "Email: $userEmailFire Name: $userNameFire Saldo: $userSaldoFire")

    Scaffold(navController)
}

@Composable
fun Info() {
    Row(modifier = Modifier
        .padding(start = 70.dp)
        .fillMaxWidth()) {
        Icon(
            Icons.Outlined.Info,
            contentDescription = "",
            Modifier.padding(top = 10.dp)
        )
        TextButton(
            onClick = {

            },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF2C2B34)
            )
        ) {
            Text(text = "Information")
        }

        Icon(
            Icons.Outlined.Notifications,
            contentDescription = "",
            Modifier.padding(top = 10.dp)
        )
        TextButton(
            onClick = {

            },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF2C2B34)
            )
        ) {
            Text(text = "Notifications")
        }
    }
}

//Para obtener la ubicacion actual del usuario
lateinit var fusedLocationClient: FusedLocationProviderClient
var lat = 36.527809
var long = -6.293338

@SuppressLint("MissingPermission")
fun obtenerUbicacion(context: Context) {

    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            lat = location.latitude
            long = location.longitude
            // hacer algo con la latitud y longitud
        } else {
            Toast.makeText(context, "Ubicación no encontrada", Toast.LENGTH_SHORT).show()
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun nearestCar(dataViewModel: DataViewModel = viewModel(), navController: NavController) {

    fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocalContext.current)

    obtenerUbicacion(LocalContext.current)

    Log.i("Ubicacion actual", lat.toString() + long.toString())

    var ubica = LatLng(lat, long)

    val getData = dataViewModel.state.value

    // Ordenar la lista de Car según su distancia a la ubicacion del usuario
    val sortedCarList = getData.sortedBy { car ->
        val carLocation = LatLng(car!!.latitud, car.longitud)
        val distanceArray = FloatArray(1)
        Location.distanceBetween(
            ubica.latitude,
            ubica.longitude,
            carLocation.latitude,
            carLocation.longitude,
            distanceArray
        )
        return@sortedBy distanceArray[0]
    }

    // Obtener el objeto Car más cercano a myLocation
    val closestCar = sortedCarList.firstOrNull()

    var precioCar = rememberSaveable { mutableStateOf("10") }
    var imgCar =
        rememberSaveable { mutableStateOf("https://www.pngplay.com/wp-content/uploads/13/2018-Tesla-Model-S-Transparent-PNG.png") }
    var marcaCar = rememberSaveable { mutableStateOf("Tesla") }
    var modeloCar = rememberSaveable { mutableStateOf("Model S") }
    var bateriaCar = rememberSaveable { mutableStateOf("85") }
    var motorCar = rememberSaveable { mutableStateOf("Eléctrico") }
    var aceleracionCar = rememberSaveable { mutableStateOf("2,1 s") }
    var maleteroCar = rememberSaveable { mutableStateOf("600 Litros") }
    var cargaCar = rememberSaveable { mutableStateOf("Carga rápida") }
    var distCar = rememberSaveable { mutableStateOf(2.0) }


    val scope = rememberCoroutineScope()

    // Verificar si se encontró un objeto Car cercano
    if (closestCar != null) {

        val distancia1 = SphericalUtil.computeDistanceBetween(
            ubica,
            LatLng(closestCar.latitud, closestCar.longitud)
        )
        Log.i("Distancia2", "%.2f".format(distancia1) + " metros")

        val distkm = distancia1 / 1000

        scope.launch {

            while (getData == null || getData.isEmpty()) {
                delay(200L) // esperar un segundo
            }
            // ejecutar código aquí
            Log.i("datos al final", getData.toString())
            precioCar.setValue(closestCar.precio)
            imgCar.setValue(closestCar.img)
            modeloCar.setValue(closestCar.modelo)
            marcaCar.setValue(closestCar.marca)
            bateriaCar.setValue(closestCar.bateria)
            motorCar.setValue(closestCar.motor)
            cargaCar.setValue(closestCar.carga)
            aceleracionCar.setValue(closestCar.aceleracion)
            maleteroCar.setValue(closestCar.maletero)

            distCar.setValue(distkm)
        }
    } else {
        // No se encontró ningún objeto Car cercano
    }

    var Arguments = MyArgs(
        marca = marcaCar.value,
        modelo = modeloCar.value,
        shouldexp = true,
        img = imgCar.value,
        motor = motorCar.value,
        distancia = distCar.value,
        bateria = bateriaCar.value,
        precio = precioCar.value,
        aceleracion = aceleracionCar.value,
        maletero = maleteroCar.value
    )
    // Convertir el objeto a JSON (String) usando Gson
    val gson = Gson()
    val myArgsJson = gson.toJson(Arguments)

    Log.i("Json prueba", myArgsJson)
    Card(
        elevation = 10.dp,
        border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp)
            .width(340.dp)
            .height(230.dp),
        backgroundColor = Color(0xFFE6E6E6),
        shape = RoundedCornerShape(20.dp),
        onClick = { navController.navigate(route = AppScreens.MapScreen.createRoute(myArgsJson)) }
    ) {
        Text(
            text = "Nearest Car",
            modifier = Modifier.padding(20.dp),
            fontWeight = FontWeight.Light
        )
        AsyncImage(
            model = imgCar.value,
            contentDescription = null,
            modifier = Modifier.padding(start = 60.dp, end = 30.dp, bottom = 50.dp)
        )
        //https://assets.stickpng.com/images/580b585b2edbce24c47b2ccc.png
        //https://www.pngmart.com/files/21/Tesla-Car-Transparent-Background.png
        Text(
            text = marcaCar.value + " ${modeloCar.value}",
            modifier = Modifier.padding(top = 165.dp, start = 20.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C2B34)
        )
        Row(
            Modifier
                .padding(top = 195.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()) {
            Icon(
                Icons.Outlined.NearMe,
                contentDescription = "",
                Modifier
                    .padding(start = 0.dp)
                    .size(18.dp)
            )
            Text(
                text = "< " + "%.2f".format(distCar.value) + " km",
                modifier = Modifier
                    .padding(start = 2.dp)
                    .weight(1f) // Ocupa todo el espacio disponible
            )
            Icon(
                Icons.Outlined.BatteryChargingFull,
                contentDescription = "",
                Modifier
                    .padding(start = 30.dp)
                    .size(18.dp)
            )
            Text(
                text = "${bateriaCar.value}%",
                modifier = Modifier.padding(start = 2.dp)
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "${precioCar.value}€/h",
                modifier = Modifier.padding(end = 0.dp),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun profileMap(navController: NavController) {
    Row(
        Modifier
            .padding(top = 20.dp, bottom = 20.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
    ) {
        Card(
            elevation = 10.dp,
            border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)
                .height(150.dp)
                .clickable { navController.navigate("account_screen") },
            backgroundColor = Color(0xFFE6E6E6),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(Modifier.padding(top = 20.dp, start = 35.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userImgFire)
                        .transformations(CircleCropTransformation())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(80.dp)
                )
            }
            Text(
                text = userNameFire,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 100.dp)
            )
            Text(
                text = "$userSaldoFire €",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 120.dp)
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Card(
            elevation = 10.dp,
            border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
            modifier = Modifier
                .weight(1f)
                .height(150.dp),
            backgroundColor = Color(0xFFE6E6E6),
            shape = RoundedCornerShape(20.dp)
        ) {
            miniMap()
        }
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
//@Preview(showBackground = true)
@Composable
fun moreCars(
    dataViewModel: DataViewModel = viewModel(),
    ubicacionActual: LatLng,
    navController: NavController
) {

    val getData = dataViewModel.state.value

    var marcaCar = rememberSaveable { mutableStateOf("Tesla") }
    var modeloCar = rememberSaveable { mutableStateOf("Model S") }
    var bateriaCar = rememberSaveable { mutableStateOf("85") }
    var imgCar = rememberSaveable { mutableStateOf("Model S") }
    var motorCar = rememberSaveable { mutableStateOf("85") }
    var precioCar = rememberSaveable { mutableStateOf("Model S") }
    var aceleracionCar = rememberSaveable { mutableStateOf("85") }
    var maleteroCar = rememberSaveable { mutableStateOf("85") }
    var distCar = rememberSaveable { mutableStateOf(2.0) }

    var marcaCar1 = rememberSaveable { mutableStateOf("Tesla") }
    var modeloCar1 = rememberSaveable { mutableStateOf("Model S") }
    var bateriaCar1 = rememberSaveable { mutableStateOf("85") }
    var imgCar1 = rememberSaveable { mutableStateOf("Model S") }
    var motorCar1 = rememberSaveable { mutableStateOf("85") }
    var precioCar1 = rememberSaveable { mutableStateOf("Model S") }
    var aceleracionCar1 = rememberSaveable { mutableStateOf("85") }
    var maleteroCar1 = rememberSaveable { mutableStateOf("85") }
    var distCar1 = rememberSaveable { mutableStateOf(2.0) }

    val scope = rememberCoroutineScope()
    scope.launch {

        while (getData == null || getData.isEmpty()) {
            delay(200L) // esperar un segundo
        }
        // ejecutar código aquí
        modeloCar.setValue(getData[0]!!.modelo)
        marcaCar.setValue(getData[0]!!.marca)
        bateriaCar.setValue(getData[0]!!.bateria)
        imgCar.setValue(getData[0]!!.img)
        motorCar.setValue(getData[0]!!.motor)
        precioCar.setValue(getData[0]!!.precio)
        aceleracionCar.setValue(getData[0]!!.aceleracion)
        maleteroCar.setValue(getData[0]!!.maletero)
        val distancia1 = SphericalUtil.computeDistanceBetween(
            ubicacionActual,
            LatLng(getData[0]!!.latitud, getData[0]!!.longitud)
        )
        distCar.setValue(distancia1 / 1000)

        modeloCar1.setValue(getData[1]!!.modelo)
        marcaCar1.setValue(getData[1]!!.marca)
        bateriaCar1.setValue(getData[1]!!.bateria)
        imgCar1.setValue(getData[1]!!.img)
        motorCar1.setValue(getData[1]!!.motor)
        precioCar1.setValue(getData[1]!!.precio)
        aceleracionCar1.setValue(getData[1]!!.aceleracion)
        maleteroCar1.setValue(getData[1]!!.maletero)
        val distancia2 = SphericalUtil.computeDistanceBetween(
            ubicacionActual,
            LatLng(getData[1]!!.latitud, getData[1]!!.longitud)
        )
        distCar1.setValue(distancia2 / 1000)
    }
    Card(
        elevation = 10.dp,
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .height(200.dp),
        backgroundColor = Color(0xFF2C2B34),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(Modifier.padding(start = 20.dp)) {
            Text(
                text = "More Cars",
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                color = Color.LightGray
            )
            fichaCar(
                marcaCar,
                modeloCar,
                imgCar,
                motorCar,
                distCar,
                bateriaCar,
                precioCar,
                aceleracionCar,
                maleteroCar,
                navController
            )
            Canvas(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
                    .padding(end = 30.dp, start = 10.dp, top = 10.dp)
            ) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = 0f, y = size.height / 2),
                    end = Offset(x = size.width, y = size.height / 2),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            fichaCar(
                marcaCar1,
                modeloCar1,
                imgCar1,
                motorCar1,
                distCar1,
                bateriaCar1,
                precioCar1,
                aceleracionCar1,
                maleteroCar1,
                navController
            )
        }
    }
}


//Este componente se usa en las demás screens solo se cambia el parámetro que se le pasa
//es el que indica al usuario en que screen está, ya que cambia el selected a true
@Composable
fun BottomNavigationBar(screen: String, navController: NavController) {
    //var ind by remember { mutableStateOf(0)}
    var Arguments = MyArgs(
        marca = "",
        modelo = "",
        shouldexp = false,
        img = "",
        motor = "",
        distancia = 0.0,
        bateria = "",
        precio = "",
        aceleracion = "",
        maletero = ""
    )
    // Convertir el objeto a JSON (String) usando Gson
    val gson = Gson()
    val myArgsJson = gson.toJson(Arguments)

    val items = listOf(
        AppScreens.HomeScreen,
        AppScreens.SearchScreen,
        AppScreens.MapScreen,
        AppScreens.AccountScreen
    )
    BottomNavigation(
        backgroundColor = Color(0xFF2C2B34),
        contentColor = Color.White,
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
            .clip(RoundedCornerShape(30))
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.Icon, contentDescription = "") },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = item.route == screen,
                onClick = {
                    Log.i("Item Route", item.route)
                    if (item.route == "map_screen?myargs={myargs}") {
                        navController.navigate(route = AppScreens.MapScreen.createRoute(myArgsJson))
                    } else {
                        navController.navigate(route = item.route)
                    }

                }
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Scaffold(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocalContext.current)

    obtenerUbicacion(LocalContext.current)

    Log.i("Ubicacion actual", lat.toString() + long.toString())

    var ubicacionActual = LatLng(lat, long)

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavigationBar("home_screen", navController) },
        floatingActionButton = { MyFab() },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        Column {
            Info()
            nearestCar(navController = navController)
            profileMap(navController = navController)
            moreCars(ubicacionActual = ubicacionActual, navController = navController)
        }
    }

}

//Este componente se usa en las demás screen se llama directamente desde aquí
@Composable
fun MyFab() {
    val showDialog = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf<Option?>(null) }

    if (showDialog.value) {
        OptionDialog(
            options = listOf(
                Option.Option1,
                Option.Option2,
                Option.Option3
            ),
            onOptionSelected = { option ->
                selectedOption.value = option
                showDialog.value = false
            },
            onDismiss = { showDialog.value = false }
        )
    }
    FloatingActionButton(
        onClick = {
            showDialog.value = true
                  },
        backgroundColor = Color(0xFFE6E6E6),
        contentColor = Color(0xFF2C2B34)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
    }
}

sealed class Option(val label: String) {
    object None : Option("Ninguna")
    object Option1 : Option("10€")
    object Option2 : Option("25€")
    object Option3 : Option("50€")
}

@Composable
fun OptionDialog(
    options: List<Option>,
    onOptionSelected: (Option) -> Unit,
    onDismiss: () -> Unit
) {
    val selectedOption = remember { mutableStateOf<Option?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Seleccione que cantidad de saldo desea introducir:",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
        ) },
        buttons = {
            Column(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                options.forEach { option ->
                    val isChecked = option == selectedOption.value

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isChecked,
                            onClick = {
                                selectedOption.value = option
                            }
                        )
                        Text(
                            text = option.label,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            selectedOption.value?.let { option ->
                                onOptionSelected(option)
                                val saldo = when(option.label){
                                    "10€" ->  10
                                    "25€" ->  25
                                    "50€" ->  50
                                    else -> { 0 }
                                }
                                updateBalance(saldo)
                            }
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF2C2B34),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Aceptar")
                    }

                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF2C2B34),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Cancelar")
                    }
                }
            }
        }
    )
}

fun updateBalance(saldo: Int) {
    Thread(kotlinx.coroutines.Runnable {
        val contract = Web3jSingleton.getCarRentalContract()
        Log.d("VALIDATED CONTRACT", "Is valid: ${contract.isValid}")

        val adminAccount = "0xdB089AA5d0c3FAC5f01FF87828801655Ebf7bB8A"
        val wei = when(saldo) {
            10 -> BigInteger.valueOf(5900000000000000)
            25 -> BigInteger.valueOf(15000000000000000)
            50 -> BigInteger.valueOf(29000000000000000)
            else -> { BigInteger.valueOf(0) }
        }

        try {
            val transactionReceipt = contract.updateBalance(adminAccount, wei).send()

            if (transactionReceipt.isStatusOK) {
                // La transacción se completó correctamente
                val transactionHash = transactionReceipt.transactionHash
                Log.i("Wei añadido OK", "Hash de la transacción: $transactionHash")
            } else {
                // La transacción falló
                Log.i("Wei no añadido KO", "No se realizó la transacción")
            }
        } catch (e: Exception) {
            // Maneja cualquier excepción que ocurra durante la ejecución de la transacción
            Log.i("No agregado saldo", "No se realizó la transacción: $e")
        }


        /*val numrentals: RemoteFunctionCall<BigInteger>? = contract.numRentals()
        Log.d("TAG", "greeting value returned: $numrentals")*/
    }).start()
}

@Composable
fun rentDialog(show: Boolean) {
    if (show){
        AlertDialog(
            onDismissRequest = { /* Acción al cerrar el diálogo */ },
            title = {
                Text(text = "Info!")
            },
            text = {
                Text(text = "Saldo agregado con éxito")
            },
            confirmButton = {
                TextButton(
                    onClick = { /* Acción al hacer clic en el botón de confirmación */ }
                ) {
                    Text(text = "Confirmar")
                }
            }
        )
    }
}

@Composable
fun miniMap() {

    fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocalContext.current)

    obtenerUbicacion(LocalContext.current)

    var ubicacionActu = LatLng(lat, long)

    //Indicar en position la ubicación actual del usuario
    //Para que el mapa se abra en su ubicación
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(ubicacionActu, 17f)
    }

    GoogleMap(modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true, mapType = MapType.HYBRID),
        googleMapOptionsFactory = { GoogleMapOptions().mapId(R.string.map_id.toString()) }
    ) {

    }

}

@Composable
fun fichaCar(
    marca: MutableState<String>,
    modelo: MutableState<String>,
    img: MutableState<String>,
    motor: MutableState<String>,
    distancia: MutableState<Double>,
    bateria: MutableState<String>,
    precio: MutableState<String>,
    aceleracion: MutableState<String>,
    maletero: MutableState<String>,
    navController: NavController
) {

    var Arguments = MyArgs(
        marca = marca.value,
        modelo = modelo.value,
        shouldexp = true,
        img = img.value,
        motor = motor.value,
        distancia = distancia.value,
        bateria = bateria.value,
        precio = precio.value,
        aceleracion = aceleracion.value,
        maletero = maletero.value
    )
    // Convertir el objeto a JSON (String) usando Gson
    val gson = Gson()
    val myArgsJson = gson.toJson(Arguments)

    Row(Modifier.fillMaxWidth()) {
        Column() {
            Text(text = "${marca.value} ${modelo.value}", fontSize = 20.sp, color = Color.White)
            Row(
                Modifier.padding(top = 5.dp)
            ) {
                Icon(
                    Icons.Outlined.NearMe,
                    contentDescription = "",
                    Modifier
                        .padding(start = 0.dp)
                        .size(18.dp),
                    tint = Color.White
                )
                Text(
                    text = "< " + "%.2f".format(distancia.value) + " km",
                    modifier = Modifier.padding(start = 2.dp),
                    color = Color.White
                )
                Icon(
                    Icons.Outlined.BatteryChargingFull,
                    contentDescription = "",
                    Modifier
                        .padding(start = 30.dp)
                        .size(18.dp),
                    tint = Color.White
                )
                Text(
                    text = "${bateria.value}%",
                    modifier = Modifier.padding(start = 2.dp),
                    color = Color.White
                )

            }
        }
        Column() {
            Box(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .padding(end = 20.dp),
                contentAlignment = Alignment.CenterEnd) {
                IconButton(
                    onClick = {
                        navController.navigate(
                            route = AppScreens.MapScreen.createRoute(
                                myArgsJson
                            )
                        )
                    },
                    modifier = Modifier.padding(start = 0.dp, top = 5.dp)
                ) {
                    Icon(
                        Icons.Outlined.ArrowCircleRight,
                        contentDescription = "",
                        Modifier
                            .size(48.dp),
                        tint = Color.White
                    )
                }
            }

        }
    }
}

var userNameFire by mutableStateOf("Name")
var userEmailFire by mutableStateOf("user@email.com")
var userImgFire by mutableStateOf("https://www.pngitem.com/pimgs/m/551-5510463_default-user-image-png-transparent-png.png")
var userMetamaskFire by mutableStateOf("")
var userSaldoFire by mutableStateOf(0.0)

@Composable
fun readUserData(documentId: String) {
    val viewModel: UserViewModel = viewModel()
    val documentData by viewModel.documentData

    LaunchedEffect(documentId) {
        viewModel.readDocument(Firebase.auth.currentUser?.uid.toString())
    }

    if (documentData != null) {

        val user = documentData
        userNameFire = user?.name.toString()
        userEmailFire = user?.email.toString()
        userImgFire = user?.img.toString()
        userMetamaskFire = user?.metamask.toString()
        userSaldoFire = user?.saldo ?: 0.0

    } else {
        CircularProgressIndicator()
    }
}
