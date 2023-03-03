package com.jetpackcompose.smartcars.ui.home.ui

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.jetpackcompose.smartcars.R
import com.jetpackcompose.smartcars.navigation.AppScreens
import com.jetpackcompose.smartcars.ui.map.ui.MyGoogleMaps

//@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    /*
    Column {
        info()
        nearestCar()
        profileMap()
        moreCars()
        //menuBar()
        BottomNavigationBar()
    }

     */
    //Solicita permisos al usuario de Ubicación
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(key1 = true) {
        permissionState.launchPermissionRequest()
    }

    if (permissionState.status.isGranted) {

    } else {

    }
    Scaffold(navController)

}

@Composable
fun info() {
    Row(modifier = Modifier.padding(start = 70.dp)) {
        Icon(
            Icons.Outlined.Info,
            contentDescription = "",
            Modifier.padding(top = 10.dp)
        )
        TextButton(onClick = {

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
        TextButton(onClick = {

        },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF2C2B34)
            )
        ) {
            Text(text = "Notifications")
        }
    }
}
@Composable
fun nearestCar(){
    Card(
        elevation = 10.dp,
        border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp)
            .width(340.dp)
            .height(230.dp),
        backgroundColor = Color(0xFFE6E6E6),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text = "Nearest Car", modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Light)
        AsyncImage(
            model = "https://assets.stickpng.com/images/580b585b2edbce24c47b2ccc.png",
            contentDescription = null,
        )
        //https://assets.stickpng.com/images/580b585b2edbce24c47b2ccc.png
        //https://www.pngmart.com/files/21/Tesla-Car-Transparent-Background.png
        Text(text = "Tesla Model S", modifier = Modifier.padding(top = 165.dp, start = 20.dp),
            fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2C2B34))
        Row(Modifier.padding(top = 195.dp)){
            Icon(
                Icons.Outlined.NearMe,
                contentDescription = "",
                Modifier
                    .padding(start = 20.dp)
                    .size(18.dp)
            )
            Text(text = "< 2km", modifier = Modifier.padding(start = 2.dp))
            Icon(
                Icons.Outlined.BatteryChargingFull,
                contentDescription = "",
                Modifier
                    .padding(start = 30.dp)
                    .size(18.dp)
            )
            Text(text = "85%", modifier = Modifier.padding(start = 2.dp))
            Text(text = "10€/h", modifier = Modifier.padding(start = 90.dp))
        }

    }
}

@Composable
fun profileMap(){
    Row(Modifier.padding(top = 20.dp, bottom = 20.dp)) {
        Card(
            elevation = 10.dp,
            border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
            modifier = Modifier
                .padding(start = 30.dp, end = 20.dp)
                .width(150.dp)
                .height(150.dp),
            backgroundColor = Color(0xFFE6E6E6),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(Modifier.padding(top = 20.dp, start = 35.dp)){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://www.pngitem.com/pimgs/m/551-5510463_default-user-image-png-transparent-png.png")
                        .transformations(CircleCropTransformation())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(80.dp)
                )
            }
            Text(text = "Name", textAlign = TextAlign.Center, modifier = Modifier.padding(top = 100.dp))
            Text(text = "150 €", textAlign = TextAlign.Center, modifier = Modifier.padding(top = 120.dp))
        }

        Card(
            elevation = 10.dp,
            border = BorderStroke(1.dp, Color(0xFFE6E6E6)),
            modifier = Modifier
                .padding(start = 10.dp, end = 30.dp)
                .width(150.dp)
                .height(150.dp),
            backgroundColor = Color(0xFFE6E6E6),
            shape = RoundedCornerShape(20.dp)
        ) {
            //Text(text = "Map", modifier = Modifier.padding(20.dp))
            /*
            AsyncImage(
                model = "https://img.freepik.com/premium-vector/street-map-with-pin-routes_23-2147622544.jpg",
                contentDescription = null,
            )
            */
            MyGoogleMaps()
        }
    }
}

@Composable
fun moreCars(){
    Card(
        elevation = 10.dp,
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp)
            .width(340.dp)
            .height(200.dp),
        backgroundColor = Color(0xFF2C2B34),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text = "More Cars", modifier = Modifier.padding(20.dp), color = Color.White)
    }
}

@Composable
fun menuBar(){
    Card(
        elevation = 10.dp,
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier
            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
            .width(340.dp)
            .height(60.dp),
        backgroundColor = Color(0xFF2C2B34),
        shape = RoundedCornerShape(20.dp)
    ) {

    }
}

//Este componente se usa en las demás screens solo se cambia el parámetro que se le pasa
//es el que indica al usuario en que screen está, ya que cambia el selected a true
@Composable
fun BottomNavigationBar(screen: String, navController: NavController) {
    //var ind by remember { mutableStateOf(0)}
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
                    navController.navigate(route = item.route)
                }
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Scaffold(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavigationBar("home_screen", navController) },
        floatingActionButton = { MyFab() },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        Column {
            info()
            nearestCar()
            profileMap()
            moreCars()
        }
    }

}

//Este componente se usa en las demás screen se llama directamente desde aquí
@Composable
fun MyFab() {
    FloatingActionButton(onClick = { /*TODO*/ }, backgroundColor = Color(0xFFE6E6E6), contentColor = Color(0xFF2C2B34)) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
    }
}