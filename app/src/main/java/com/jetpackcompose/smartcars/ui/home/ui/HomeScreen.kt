package com.jetpackcompose.smartcars.ui.home.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.jetpackcompose.smartcars.R
import com.jetpackcompose.smartcars.navigation.AppScreens

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(navController: NavController) {
    Column {
        info()
        nearestCar()
        profileMap()
        moreCars()
        menuBar()
    }

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
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp)
            .width(340.dp)
            .height(200.dp),
        backgroundColor = Color.LightGray
    ) {
        Text(text = "Nearest Car", modifier = Modifier.padding(20.dp))
    }
}

@Composable
fun profileMap(){
    Row(Modifier.padding(top = 20.dp, bottom = 20.dp)) {
        Card(
            elevation = 10.dp,
            border = BorderStroke(1.dp, Color.LightGray),
            modifier = Modifier
                .padding(start = 30.dp, end = 20.dp)
                .width(150.dp)
                .height(150.dp),
            backgroundColor = Color.LightGray
        ) {
            Text(text = "Name", modifier = Modifier.padding(20.dp))
            Text(text = "150 €", modifier = Modifier.padding(50.dp))
        }

        Card(
            elevation = 10.dp,
            border = BorderStroke(1.dp, Color.LightGray),
            modifier = Modifier
                .padding(start = 10.dp, end = 30.dp)
                .width(150.dp)
                .height(150.dp),
            backgroundColor = Color.LightGray
        ) {
            Text(text = "Map", modifier = Modifier.padding(20.dp))
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
        backgroundColor = Color(0xFF2C2B34)
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
            .padding(top = 40.dp, start = 30.dp, end = 30.dp)
            .width(340.dp)
            .height(60.dp),
        backgroundColor = Color(0xFF2C2B34)
    ) {

    }
}
