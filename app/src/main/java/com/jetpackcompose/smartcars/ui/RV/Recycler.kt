package com.jetpackcompose.smartcars.ui.RV

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.jetpackcompose.smartcars.R
import com.jetpackcompose.smartcars.ui.RV.model.Coche
import com.jetpackcompose.smartcars.ui.data.DataViewModel
import com.jetpackcompose.smartcars.ui.data.getDataFromFireStore
import com.jetpackcompose.smartcars.ui.data.model.Car
import com.jetpackcompose.smartcars.ui.map.ui.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RvScreen(dataViewModel: DataViewModel = viewModel()) {

    var getData = dataViewModel.state.value
    var imgCar =
        rememberSaveable { mutableStateOf("https://www.pngplay.com/wp-content/uploads/13/2018-Tesla-Model-S-Transparent-PNG.png") }
    var marcaCar = rememberSaveable { mutableStateOf("Tesla") }
    var modeloCar = rememberSaveable { mutableStateOf("Model S") }

    val context = LocalContext.current
    val rvState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    LazyColumn(
        state = rvState,
        verticalArrangement = Arrangement.spacedBy(7.dp),
        modifier = Modifier.background(Color.White),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(getData) { Car ->
            ItemCoche(Car = Car!!) {
                Toast.makeText(context, it.marca, Toast.LENGTH_LONG).show()
            }
        }
    }

//    val showbutton by remember {
//        derivedStateOf {
//            rvState.firstVisibleItemIndex > 1
//        }
//    }
//    if (showbutton) {
//        Button(
//            onClick = { coroutineScope.launch { rvState.animateScrollToItem(9) } },
//            modifier = Modifier.padding(15.dp)
//        ) {
//            Text(text = "Bajar")
//        }
//    }

}

fun getCoches(): List<Car> {

    return listOf(
        Car(),
        Car(),
        Car(),
        Car(),
        Car(),
        Car(),


    )
}



@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ItemCoche(Car: Car, dataViewModel: DataViewModel = viewModel(), onItemSelected: (Car) -> Unit) {


    var getData = dataViewModel.state.value
//
//
    var imgCar = rememberSaveable { mutableStateOf("https://www.pngplay.com/wp-content/uploads/13/2018-Tesla-Model-S-Transparent-PNG.png") }
    var marcaCar = rememberSaveable { mutableStateOf("Tesla") }
    var modeloCar = rememberSaveable { mutableStateOf("Model S") }


    imgCar.setValue(Car.img)
    marcaCar.setValue(Car.marca)
    modeloCar.setValue(Car.modelo)

//
//    val scope = rememberCoroutineScope()
//    scope.launch {
//
//        while (getData == null || getData.isEmpty()) {
//            delay(200L) // esperar un segundo
//        }
//        // ejecutar código aquí
//        for (item in getData) {
//            modeloCar.setValue(item!!.modelo)
//            marcaCar.setValue(item!!.marca)
//            imgCar.setValue(item!!.img)
//
//            val entrees = mutableListOf<String>()
//            entrees.add(marcaCar.value)
//            entrees.add(modeloCar.value)
//            Log.i("Prueba datos" , entrees.toString())
//        }
//    }
//    Log.i("Datos en recycler", marcaCar.toString())


//    val scope = rememberCoroutineScope()
//    scope.launch {
//
//        while (getData == null || getData.isEmpty()) {
//            delay(200L) // esperar un segundo
//        }
//        // ejecutar código aquí
//
//        imgCar.setValue(getData[0]!!.img)
//        marcaCar.setValue(getData[0]!!.marca)
//        modeloCar.setValue(getData[0]!!.modelo)
//
//        Log.i("Datos en recycler", marcaCar.toString())
//
//    }


    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color(0xFF2C2B34),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        border = BorderStroke(6.dp, Color.Black),

        ) {
        Row {
//            Image(
//                painter = painterResource(id = Car.img),
//                contentDescription = null,
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .padding(8.dp)
//                    .size(100.dp)
//                    .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
//            )
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
