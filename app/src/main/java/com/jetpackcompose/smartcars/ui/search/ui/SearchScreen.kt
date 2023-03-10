package com.jetpackcompose.smartcars.ui.search.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jetpackcompose.smartcars.navigation.AppScreens
import com.jetpackcompose.smartcars.ui.RV.RvScreen
import com.jetpackcompose.smartcars.ui.home.ui.*
import androidx.compose.material.*

//import com.jetpackcompose.smartcars.ui.home.ui.Scaffold

@Composable
fun SearcScreen(navController: NavController) {
    Scaffold(navController)
}

//Este componente es para la top bar de la screen del recycler view
@Composable
fun CustomTopAppBar(screen: String, navController: NavController) {
    //var ind by remember { mutableStateOf(0)}
    val items = listOf(
        AppScreens.HomeScreen,
        AppScreens.SearchScreen,
        AppScreens.MapScreen,
        AppScreens.AccountScreen
    )
    TopAppBar(
        backgroundColor = Color(0xFF2C2B34),
        contentColor = Color.White,
        elevation = 0.dp,
        title = {
            Text(
                text = "Available Cars"
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigate(route = AppScreens.HomeScreen.route) }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Atrás")
            }
        },
        actions = {

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
            }
        }
    )
}

//@Preview(showBackground = true, showSystemUi = true)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Scaffold(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {CustomTopAppBar(screen = "search_screen", navController )}

    ) {
        Column {
            RvScreen()
        }
    }

}