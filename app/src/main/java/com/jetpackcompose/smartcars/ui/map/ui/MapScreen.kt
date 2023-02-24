package com.jetpackcompose.smartcars.ui.map.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.FabPosition
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jetpackcompose.smartcars.ui.home.ui.BottomNavigationBar
import com.jetpackcompose.smartcars.ui.home.ui.MyFab

@Composable
fun MapScreen() {
    Scaffold()
}

@Preview(showBackground = true, showSystemUi = true)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Scaffold() {
    val scaffoldState = rememberScaffoldState()

    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavigationBar("map_screen") },
        floatingActionButton = { MyFab() },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        Column {

        }
    }

}