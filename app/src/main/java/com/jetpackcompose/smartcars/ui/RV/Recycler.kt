package com.jetpackcompose.smartcars.ui.RV

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.SphericalUtil
import com.jetpackcompose.smartcars.navigation.AppScreens
import com.jetpackcompose.smartcars.ui.data.DataViewModel
import com.jetpackcompose.smartcars.ui.data.model.Car
import com.jetpackcompose.smartcars.ui.data.model.MyArgs
import com.jetpackcompose.smartcars.ui.home.ui.fichaCar
import com.jetpackcompose.smartcars.ui.home.ui.lat
import com.jetpackcompose.smartcars.ui.home.ui.long
import com.jetpackcompose.smartcars.ui.map.ui.setValue
import kotlinx.coroutines.launch


//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RvScreen(
    dataViewModel: DataViewModel = viewModel(),
    navController: NavController
) {

    var getData = dataViewModel.state.value
    val rvState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LazyColumn(
        state = rvState,
        verticalArrangement = Arrangement.spacedBy(7.dp),
        modifier = Modifier.background(Color.White),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)

    ) {
        // Esto hace que genere una card en la lazycolumn con los datos del array
        // getData del viewmodel que son los coches registrados en firebase.
        val ubica = LatLng(lat, long)
        items(getData) { Car ->
            ItemCoche(Car = Car!!, onClick = {

                val distancia1 = SphericalUtil.computeDistanceBetween(
                    ubica,
                    LatLng(Car.latitud, Car.longitud)
                )
                Log.i("Distancia2", "%.2f".format(distancia1) + " metros")

                val distkm = distancia1 / 1000

                val Arguments = MyArgs(
                    marca = Car.marca,
                    modelo = Car.modelo,
                    shouldexp = true,
                    img = Car.img,
                    motor = Car.motor,
                    distancia = distkm,
                    bateria = Car.bateria,
                    precio = Car.precio,
                    aceleracion = Car.aceleracion,
                    maletero = Car.maletero
                )
                // Convertir el objeto a JSON (String) usando Gson
                val gson = Gson()
                val myArgsJson = gson.toJson(Arguments)
                navController.navigate(route = AppScreens.MapScreen.createRoute(myArgsJson))
            })
        }
    }

    val showbutton by remember {
        derivedStateOf {
            rvState.firstVisibleItemIndex > 1
        }
    }
    if (showbutton) {
        Button(
            onClick = { scope.launch { rvState.animateScrollToItem(9) } },
            modifier = Modifier.padding(15.dp)
        ) {
            Text(text = "Bajar")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ItemCoche(Car: Car, onClick: () -> Unit) {

    var imgCar =
        rememberSaveable { mutableStateOf("https://www.pngplay.com/wp-content/uploads/13/2018-Tesla-Model-S-Transparent-PNG.png") }
    var marcaCar = rememberSaveable { mutableStateOf("Tesla") }
    var modeloCar = rememberSaveable { mutableStateOf("Model S") }

    imgCar.setValue(Car.img)
    marcaCar.setValue(Car.marca)
    modeloCar.setValue(Car.modelo)


    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color(0xFF2C2B34),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        border = BorderStroke(6.dp, Color.Black),
        onClick = onClick
    ) {
        Row {
            AsyncImage(
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(16.dp))),
                model = imgCar.value,
            );
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {

                Text(
                    text = marcaCar.value,
                    style = MaterialTheme.typography.h5,
                    color = Color.White
                )
                Text(
                    text = modeloCar.value,
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White
                )

            }
        }
    }
}
